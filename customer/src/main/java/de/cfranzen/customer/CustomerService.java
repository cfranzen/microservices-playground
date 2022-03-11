package de.cfranzen.customer;

import de.cfranzen.amqp.RabbitMqMessageProducer;
import de.cfranzen.clients.fraud.FraudCheckResult;
import de.cfranzen.clients.fraud.FraudClient;
import de.cfranzen.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository repository;

    private final FraudClient fraudClient;

    private final RabbitMqMessageProducer messageProducer;

    @NewSpan
    public void registerCustomer(@SpanTag("request") CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        repository.saveAndFlush(customer);

        FraudCheckResult response = fraudClient.isFraudster(customer.getId());
        if (response.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest payload = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to microservices-playground...",
                        customer.getFirstName())
        );
        messageProducer.publish(payload, "internal.exchange", "internal.notification.routing-key");
    }
}

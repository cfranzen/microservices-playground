package de.cfranzen.customer;

import de.cfranzen.clients.fraud.FraudCheckResponse;
import de.cfranzen.clients.fraud.FraudClient;
import de.cfranzen.clients.notification.NotificationClient;
import de.cfranzen.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository repository;

    private final FraudClient fraudClient;

    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        repository.saveAndFlush(customer);

        FraudCheckResponse response = fraudClient.isFraudster(customer.getId());
        if (response.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: make it async. i.e add to queue
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to microservices-playground...",
                                customer.getFirstName())
                )
        );
    }
}

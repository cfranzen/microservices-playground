package de.cfranzen.customer;

import de.cfranzen.clients.fraud.FraudCheckResponse;
import de.cfranzen.clients.fraud.FraudClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository repository;

    private final FraudClient client;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        repository.saveAndFlush(customer);

        FraudCheckResponse response = client.isFraudster(customer.getId());
        if (response.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
    }
}

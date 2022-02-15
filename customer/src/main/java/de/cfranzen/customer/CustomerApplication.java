package de.cfranzen.customer;

import de.cfranzen.clients.ClientsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
        scanBasePackages = {
                "de.cfranzen.customer",
                "de.cfranzen.amqp"
        }
)
@EnableEurekaClient
@Import(ClientsConfig.class)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

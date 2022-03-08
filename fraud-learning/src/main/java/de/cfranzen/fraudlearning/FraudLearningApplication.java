package de.cfranzen.fraudlearning;

import de.cfranzen.clients.ClientsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        scanBasePackages = {
                "de.cfranzen.fraudlearning",
                "de.cfranzen.kafka"
        }
)
@EnableEurekaClient
@EnableScheduling
@Import(ClientsConfig.class)
public class FraudLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudLearningApplication.class, args);
    }
}

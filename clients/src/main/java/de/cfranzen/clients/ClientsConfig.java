package de.cfranzen.clients;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active:default}.properties")
})
@EnableFeignClients(basePackages = "de.cfranzen.clients")
public class ClientsConfig {
}

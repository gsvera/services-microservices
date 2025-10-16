package com.esthetic.servicesmicroservices.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EnvConfig {
    @Value("${my.property.api.gateway}")
    private String apiGateway;
    @Value("${env.exponotificationurl}")
    private String expoNotificationUrl;
}

package com.freedom.mastertransfertorder.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gateway-api")
@Getter
@Setter
public class GatewayConfigurationProperties {
    private String transfertOrderUrl;
}

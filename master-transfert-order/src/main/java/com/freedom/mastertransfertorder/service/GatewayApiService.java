package com.freedom.mastertransfertorder.service;

import com.freedom.mastertransfertorder.configuration.GatewayConfigurationProperties;
import com.freedom.mastertransfertorder.model.TransferOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author bean.nguyen
 * <p>
 * Responsible to call gateway-api.
 */
@Service
public class GatewayApiService {

    private RestTemplate customRestTemplate;
    private GatewayConfigurationProperties gatewayConfigurationProperties;

    @Autowired
    public GatewayApiService(@Qualifier("customRestTemplate") RestTemplate customRestTemplate,
                             GatewayConfigurationProperties gatewayConfigurationProperties) {
        this.customRestTemplate = customRestTemplate;
        this.gatewayConfigurationProperties = gatewayConfigurationProperties;
    }

    @Async
    public void send(TransferOrder transferOrder) {
        customRestTemplate.postForObject(gatewayConfigurationProperties.getTransfertOrderUrl(),
                                         transferOrder,
                                         String.class);
    }
}

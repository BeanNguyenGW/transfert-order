package com.example.gatewaytransfertorder.service;

import com.example.gatewaytransfertorder.model.TransferOrder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;

/**
 * @author bean.nguyen
 * <p>
 * Responsible to call gateway-api.
 */
@Service
@Log
public class MasterApiService {

    private RestTemplate customRestTemplate;

    @Autowired
    public MasterApiService(@Qualifier("customRestTemplate") RestTemplate customRestTemplate) {
        this.customRestTemplate = customRestTemplate;
    }

    public void syncToMaster(TransferOrder transferOrder) {
        log.log(Level.INFO, "sync data to master");
    }
}

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
 * Responsible to call branch-api.
 */
@Service
@Log
public class BranchApiService {

    private RestTemplate customRestTemplate;

    @Autowired
    public BranchApiService(@Qualifier("customRestTemplate") RestTemplate customRestTemplate) {
        this.customRestTemplate = customRestTemplate;
    }

    public boolean canTransferOrder(TransferOrder transferOrder) {
        log.log(Level.INFO, "Check whether can transfer order or not");
        return true;
    }

    public void deleteOrder(TransferOrder transferOrder) {
        log.log(Level.INFO, "Delete order.");
    }
}

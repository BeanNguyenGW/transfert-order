package com.example.gatewaytransfertorder.service;

import com.example.gatewaytransfertorder.model.TransferOrder;
import com.example.gatewaytransfertorder.request.TransferOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bean.nguyen
 */
@Service
public class TransferOrderProcessorService {

    private RabbitMqSender rabbitMqSender;

    @Autowired
    public TransferOrderProcessorService(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    /**
     * Splits the transfert order request into small chunk
     *
     * @param transferOrder The request.
     */
    public void process(TransferOrder transferOrder) {
        rabbitMqSender.send(transferOrder);
    }
}

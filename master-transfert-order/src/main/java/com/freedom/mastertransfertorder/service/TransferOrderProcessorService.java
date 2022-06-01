package com.freedom.mastertransfertorder.service;

import com.freedom.mastertransfertorder.model.TransferOrder;
import com.freedom.mastertransfertorder.request.TransferOrderRequest;
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
     * @param transferOrderRequest The request.
     */
    public void split(TransferOrderRequest transferOrderRequest) {
        final String targetUrl = "test";
        transferOrderRequest.getTransferOrderIds().forEach(order -> {
            TransferOrder transferOrder = new TransferOrder();
            transferOrder.setOrderId(order);
            transferOrder.setSourceId(transferOrderRequest.getSourceId());
            transferOrder.setTargetId(transferOrderRequest.getTargetId());
            transferOrder.setTargetUrl(targetUrl);
            rabbitMqSender.send(transferOrder);
        });

    }
}

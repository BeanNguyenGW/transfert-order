package com.freedom.mastertransfertorder.service;

import com.freedom.mastertransfertorder.model.TransferOrder;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * @author bean.nguyen
 */
@Component
@Log
public class TransfertOrderWorker {
    private GatewayApiService gatewayApiService;

    @Autowired
    public TransfertOrderWorker(GatewayApiService gatewayApiService) {
        this.gatewayApiService = gatewayApiService;
    }

    /**
     * Receives a message.
     *
     * @param message Message that receives from queue.
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(final TransferOrder message) {
        log.log(Level.INFO, "Message receive: {0}", message);
        gatewayApiService.send(message);
    }
}

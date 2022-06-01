package com.example.gatewaytransfertorder.service;

import com.example.gatewaytransfertorder.model.TransferOrder;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Pulishes messages to queue.
     *
     * @param transferOrder The transfert order.
     */
    public void send(TransferOrder transferOrder) {
        rabbitTemplate.convertAndSend("", queueName, transferOrder);
    }
}

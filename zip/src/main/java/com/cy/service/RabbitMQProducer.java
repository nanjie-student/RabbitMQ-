package com.cy.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name:dockerQueue}")
    private String queueName;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println(" [x] Sent: " + message);
    }
}

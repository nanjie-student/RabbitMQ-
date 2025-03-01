package com.spring.dataconsistency.rabbitMQ;

import com.spring.dataconsistency.pojo.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderToQueue(Order order){
        rabbitTemplate.convertAndSend(order);
        System.out.println("Sent order to queue"+ order);
    }
}

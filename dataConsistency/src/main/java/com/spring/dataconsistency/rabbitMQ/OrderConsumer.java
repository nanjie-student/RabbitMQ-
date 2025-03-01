package com.spring.dataconsistency.rabbitMQ;

import com.spring.dataconsistency.pojo.Order;
import com.spring.dataconsistency.service.InventoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final InventoryService inventoryService;

    public OrderConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RabbitListener(queues = "order_queue")
    public void processOrder(Order order) {
        System.out.println("✅ Processing order: " + order.getOrderId());
        inventoryService.updateInventory(order.getProductId(), order.getQuantity());
    }

}

package com.spring.dataconsistency.controller;

import com.spring.dataconsistency.pojo.Order;
import com.spring.dataconsistency.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("./orders")
public class orderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.ok("Order created successfully");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/platform/{platform}")
    public ResponseEntity<List<Order>> getOrdersByPlatform(@PathVariable String platform) {
        List<Order> orders = orderService.getOrdersByPlatform(platform);
        return ResponseEntity.ok(orders);
    }

}

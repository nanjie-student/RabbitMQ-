package com.spring.dataconsistency.controller;

import com.spring.dataconsistency.pojo.Order;
import com.spring.dataconsistency.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("./webhook")
public class webhookController {

    @Autowired
    private OrderService orderService;

    @PostMapping("./shopify/orderCreated")
    public String handleShopifyOrder(@RequestBody Order order){
        //System.out.println("Shopify order created"+ order);
        orderService.processOrder(order);
        //return "success";


    }
    @PostMapping("./etsy/orderCreated")
    public String handleShopifyOrder(@RequestBody Order order){
        System.out.println("Etsy order created"+ order);
        orderService.processOrder(order);
        // ResponseEntity.ok("Etsy order created");


    }



}

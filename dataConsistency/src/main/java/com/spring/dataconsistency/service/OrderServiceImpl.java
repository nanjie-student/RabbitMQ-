package com.spring.dataconsistency.service;

import com.spring.dataconsistency.dao.OrderRepository;
import com.spring.dataconsistency.pojo.Order;
import com.spring.dataconsistency.rabbitMQ.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;


    @Override
    @Transactional
    public void processOrder(Order order) {
        orderRepository.save(order);
        //发送到RabbitMQ进行异步库存更新
        orderProducer.sendOrderToQueue(order);
        //System.out.println("📩 Order saved and added to queue: " + order);

    }
}

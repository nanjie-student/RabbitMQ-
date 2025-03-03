package com.spring.dataconsistency.service;

import com.spring.dataconsistency.dao.OrderRepository;
import com.spring.dataconsistency.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {



    void createOrder(Order order); // 创建订单
    Order getOrderById(String orderId); // 根据订单 ID 获取订单
    List<Order> getOrdersByPlatform(String platform); // 根据平台获取订单

    @Transactional
    void updateOrderStatus(String orderId, String newStatus);
}

package com.spring.dataconsistency.service;

import com.spring.dataconsistency.dao.OrderRepository;
import com.spring.dataconsistency.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopifyClient shopifyClient;

    @Autowired
    private EtsyClient etsyClient;


    @Override
    public void createOrder(Order order) {
        //set order created time
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

    }

    @Override
    public Order getOrderById(String orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get(); // 返回订单
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    @Override
    public List<Order> getOrdersByPlatform(String platform) {
        return orderRepository.findByPlatform(platform);
    }
    @Transactional
    @Override
    public void updateOrderStatus(String orderId, String newStatus) {
        // 更新本地订单状态
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(newStatus);
        orderRepository.save(order);

        // 同步订单状态到 Shopify 和 Etsy
        shopifyClient.updateOrderStatus(orderId, newStatus);
        etsyClient.updateOrderStatus(orderId, newStatus);
    }
}

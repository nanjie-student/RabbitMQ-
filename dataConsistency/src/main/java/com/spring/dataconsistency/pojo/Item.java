package com.spring.dataconsistency.pojo;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 商品项 ID

    private String productId; // 商品 ID
    private String productName; // 商品名称
    private int quantity; // 商品数量
    private double price; // 商品单价

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 关联的订单
}

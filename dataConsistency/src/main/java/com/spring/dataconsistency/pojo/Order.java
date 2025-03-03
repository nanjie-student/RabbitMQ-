package com.spring.dataconsistency.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 使用 Long 作为主键

    private String orderId; // 平台订单 ID（如 Shopify 或 Etsy 的订单 ID）

    private String platform; // 平台名称，如 "shopify" 或 "etsy"

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> lineItems; // 订单中的商品项列表

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // 订单创建时间，自动设置

    // 添加一个便捷方法，用于关联 Order 和 Item
    public void addLineItem(Item item) {
        item.setOrder(this);
        this.lineItems.add(item);
    }

}

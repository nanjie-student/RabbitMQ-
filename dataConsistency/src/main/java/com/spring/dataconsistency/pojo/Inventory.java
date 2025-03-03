package com.spring.dataconsistency.pojo;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 库存 ID

    @Column(name = "product_id", unique = true, nullable = false)
    private String productId; // 商品 ID

    @Column(nullable = false)
    private int stock; // 库存数量

    @Version // 乐观锁版本字段
    private int version;
}

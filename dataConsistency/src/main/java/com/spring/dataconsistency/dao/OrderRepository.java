package com.spring.dataconsistency.dao;

import com.spring.dataconsistency.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByPlatform(String platform);//according platform to search orders
}
1
package com.spring.dataconsistency.dao;

import com.spring.dataconsistency.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends JpaRepository<Order, Integer> {
}

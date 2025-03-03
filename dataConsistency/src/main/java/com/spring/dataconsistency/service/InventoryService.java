package com.spring.dataconsistency.service;

import com.spring.dataconsistency.aop.annotation.Loggable;
import com.spring.dataconsistency.pojo.Inventory;
import org.springframework.transaction.annotation.Transactional;


public interface InventoryService {

    @Transactional
    @Loggable("Updating inventory")
    void updateInventory(String productId, int quantity); // 扣减库存
    @Loggable("Fetching inventory")
    int getStock(String productId); // 获取库存
    void syncInventory(String productId, int quantity); // 同步库存到平台
}

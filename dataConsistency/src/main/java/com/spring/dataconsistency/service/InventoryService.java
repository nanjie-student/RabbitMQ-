package com.spring.dataconsistency.service;

import com.spring.dataconsistency.pojo.Inventory;

public interface InventoryService {

    void findByID(String id);
    void updateInventory(int productId, int quantity);
}

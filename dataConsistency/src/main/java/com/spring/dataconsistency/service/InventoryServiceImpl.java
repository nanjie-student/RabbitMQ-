package com.spring.dataconsistency.service;


import com.spring.dataconsistency.dao.InventoryRepository;
import com.spring.dataconsistency.pojo.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    InventoryRepository inventoryRepository;


    @Override
    @Transactional
    public void updateInventory(int productId, int quantity) {
        Inventory inventory = inventoryRepository.findById(productId);
        if(inventory.getQuantity() < quantity){
            System.out.println("Not enough products");;
        }
        inventory.setQuantity(inventory.getQuantity()-quantity);
        inventoryRepository.save(inventory);
        //System.out.println("✅ Updated inventory: Product " + productId + " new quantity: " + inventory.getQuantity());
    }

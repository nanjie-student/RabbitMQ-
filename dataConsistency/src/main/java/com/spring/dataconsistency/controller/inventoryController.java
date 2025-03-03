package com.spring.dataconsistency.controller;


import com.spring.dataconsistency.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class inventoryController {

    @Autowired
    private InventoryService inventoryService;

    //仓库的增删改查
    @PostMapping("/update")
    public ResponseEntity<String> updateInventory(@RequestParam String productId, @RequestParam int newStock) {
        inventoryService.updateInventory(productId, newStock);
        return ResponseEntity.ok("Inventory updated successfully");
    }
    @GetMapping("./getStock")
    public ResponseEntity<String> getStock(@RequestParam String productId) {
        inventoryService.getStock(productId);
        return ResponseEntity.ok("Stock retrieved successfully");
    }
}

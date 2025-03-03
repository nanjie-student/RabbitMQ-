package com.spring.dataconsistency.service;


import com.spring.dataconsistency.aop.annotation.Loggable;
import com.spring.dataconsistency.dao.InventoryRepository;
import com.spring.dataconsistency.pojo.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    private ShopifyClient shopifyClient;



    @Override
    @Loggable("update inventory")
    @Retryable(value = {RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void updateInventory(String productId, int newStock) {
        // 更新本地库存
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        inventory.setStock(newStock);
        inventoryRepository.save(inventory);
        // 同步库存到 Shopify
        try {
            shopifyClient.updateInventory(productId, newStock);
        } catch (Exception e) {
            // 记录错误日志
            logger.error("Failed to sync inventory to Shopify for product: " + productId, e);

            // 抛出异常，触发事务回滚
            throw new RuntimeException("Failed to sync inventory to Shopify", e);
        }
    }
    // 重试失败后的处理逻辑
    @Recover
    public void recoverUpdateInventory(RuntimeException e, String productId, int newStock) {
        // 记录错误日志
        System.err.println("Failed to update inventory after retries: " + e.getMessage());

        // 发送通知或执行其他操作
    }

    @Override
    public int getStock(String productId) {
        // 从本地数据库查询库存
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return inventory.getStock();
    }

    @Override
    public void syncInventory(String productId, int quantity) {

    }
}




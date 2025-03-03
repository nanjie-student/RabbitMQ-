package com.spring.dataconsistency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dataconsistency.pojo.Order;
import com.spring.dataconsistency.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    //日志处理
    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String SHOPIFY_SECRET = "your_shopify_webhook_secret"; // Shopify Webhook 的密钥

    @PostMapping("/shopify/orderCreated")
    public ResponseEntity<String> handleShopifyOrder(
            @RequestBody String payload,
            @RequestHeader("X-Shopify-Hmac-Sha256") String hmacHeader) {

        // 验证 HMAC 签名
        if (!verifyHmac(payload, hmacHeader)) {
            return ResponseEntity.status(401).body("Invalid HMAC signature");
        }

        // 解析 JSON 数据
        ShopifyOrder order = parseShopifyOrder(payload);

        // 将订单消息发送到 RabbitMQ
        rabbitTemplate.convertAndSend("shopify.order.created", order);
        return ResponseEntity.ok("Webhook received and order queued");
    }

    // 验证 HMAC 签名
    private boolean verifyHmac(String payload, String hmacHeader) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SHOPIFY_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] digest = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String calculatedHmac = Base64.getEncoder().encodeToString(digest);
            return calculatedHmac.equals(hmacHeader);
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify HMAC signature", e);
        }
    }

    // 解析 Shopify 订单
    private ShopifyOrder parseShopifyOrder(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payload, ShopifyOrder.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Shopify order", e);
        }
    }
}

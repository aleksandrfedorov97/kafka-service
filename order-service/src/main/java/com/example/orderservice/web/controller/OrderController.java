package com.example.orderservice.web.controller;

import com.example.ordercore.model.kafka.messages.OrderEvent;
import com.example.orderservice.web.model.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    @Value("${app.kafka.topics.orderTopic}")
    private String orderTopicName;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody OrderRequest request) {
        OrderEvent orderEvent = OrderEvent.builder()
                .product(request.getProduct())
                .quantity(request.getQuantity())
                .build();

        kafkaTemplate.send(orderTopicName, orderEvent);

        return ResponseEntity.ok("Order created");
    }
}

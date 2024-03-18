package com.example.ordercore.model.kafka.messages;

import lombok.Data;

import java.time.Instant;

@Data
public class OrderStatus {
    private String status;
    private Instant date;
}

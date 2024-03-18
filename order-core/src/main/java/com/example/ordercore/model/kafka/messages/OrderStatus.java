package com.example.ordercore.model.kafka.messages;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrderStatus {
    private String status;
    private Instant date;
}

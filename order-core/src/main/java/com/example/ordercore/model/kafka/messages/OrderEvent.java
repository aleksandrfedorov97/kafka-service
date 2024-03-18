package com.example.ordercore.model.kafka.messages;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEvent {
    String product;
    Integer quantity;
}

package com.example.orderservice.web.model;

import lombok.Data;

@Data
public class OrderRequest {
    String product;
    Integer quantity;
}

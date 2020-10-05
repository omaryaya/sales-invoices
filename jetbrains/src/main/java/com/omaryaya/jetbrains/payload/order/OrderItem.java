package com.omaryaya.jetbrains.payload.order;

import lombok.Data;

@Data
public class OrderItem {

    private Long categoryId;

    private int quantity;

    private Long price;
    
}

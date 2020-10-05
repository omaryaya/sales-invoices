package com.omaryaya.jetbrains.payload.order;

import lombok.Data;

@Data
public class ItemRequest {

    private Long productId;
    private Long orderId;
    private int quantity;
    
}

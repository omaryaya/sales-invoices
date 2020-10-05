package com.omaryaya.jetbrains.payload.order;

import java.util.List;

import lombok.Data;

@Data
public class OrderNewRequest {

    private Long id;
    
    private String currency;

    private String referenceNumber;

    private Long customerId;

    private List<OrderItem> items;
    
}

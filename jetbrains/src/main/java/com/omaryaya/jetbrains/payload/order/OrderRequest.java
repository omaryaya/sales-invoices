package com.omaryaya.jetbrains.payload.order;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private String currency;

    private String referenceNumber;

    private List<ItemRequest> items;

    private Long customerId;
    
}

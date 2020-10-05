package com.omaryaya.jetbrains.payload.order;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private String currency;

    private String referenceNumber;

    private List<ItemRequest> items;

    private Long customerId;
    
}

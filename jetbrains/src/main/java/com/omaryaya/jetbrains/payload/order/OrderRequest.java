package com.omaryaya.jetbrains.payload.order;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.omaryaya.jetbrains.payload.product.ProductRequest;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private String currency;

    private String referenceNumber;

    @NotNull
    @Valid
    private List<ProductRequest> products;
    
}

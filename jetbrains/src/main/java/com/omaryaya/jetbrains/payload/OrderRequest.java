package com.omaryaya.jetbrains.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private String currency;

    @NotNull
    @Valid
    private List<ProductRequest> products;
    
}

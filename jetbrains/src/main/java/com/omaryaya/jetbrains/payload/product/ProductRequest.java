package com.omaryaya.jetbrains.payload.product;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductRequest {

    @NotNull
    private Long id;

    private String name;
    
}

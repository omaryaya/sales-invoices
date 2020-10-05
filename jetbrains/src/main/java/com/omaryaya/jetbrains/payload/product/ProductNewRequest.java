package com.omaryaya.jetbrains.payload.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class ProductNewRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long categoryId;

    private String sku;

    private Double price;
    
}

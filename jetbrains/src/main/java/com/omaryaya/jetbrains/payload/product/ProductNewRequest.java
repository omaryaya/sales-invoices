package com.omaryaya.jetbrains.payload.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class ProductNewRequest {

    @NotBlank
    private String name;

    private String sku;

    @NotNull
    private Double price;
    
}

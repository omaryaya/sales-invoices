package com.omaryaya.jetbrains.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.omaryaya.jetbrains.entity.ProductCategory;

import lombok.Data;

@Data
public class ProductNewRequest {

    @NotBlank
    private String name;

    @NotNull
    private ProductCategory category;

    private String sku;

    private Double price;
    
}

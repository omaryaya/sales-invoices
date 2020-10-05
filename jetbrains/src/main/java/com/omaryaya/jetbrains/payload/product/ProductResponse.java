package com.omaryaya.jetbrains.payload.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    
    private Long id;
    private String name;
    private String sku;
    private Double price;
    
}

package com.omaryaya.jetbrains.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    
    private Long id;
    private String name;
    private ProductCategoryResponse category;
    private String sku;
    private Double price;
    private OrderResponse order;
    
}

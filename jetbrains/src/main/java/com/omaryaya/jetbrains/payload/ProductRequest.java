package com.omaryaya.jetbrains.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;
    
}

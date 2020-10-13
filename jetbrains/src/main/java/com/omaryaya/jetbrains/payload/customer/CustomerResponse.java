package com.omaryaya.jetbrains.payload.customer;


import java.util.List;

import com.omaryaya.jetbrains.entity.Order;

import lombok.Data;
import lombok.Value;

@Data
public class CustomerResponse {
    
    private Long id;
    private String name;
    private String address;
    private List<Order> orders;
    
}

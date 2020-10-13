package com.omaryaya.jetbrains.model;

import lombok.Data;
import lombok.Value;

@Data
public class OrderStatusCount {

    private OrderStatus orderStatus;
    private Long count;
    
}

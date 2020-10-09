package com.omaryaya.jetbrains.payload.model;

import java.math.BigInteger;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class OrderProfit {

    private BigInteger orderId;
    private Double profit;
    
}

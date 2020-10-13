package com.omaryaya.jetbrains.payload.model;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Data
@EqualsAndHashCode
@Value
public class OrderProfit {

    private BigInteger orderId;
    private Double profit;
    
}

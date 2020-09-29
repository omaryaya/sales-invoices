package com.omaryaya.jetbrains.payload;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerProfileResponse {
    private Long id;
    private String name;
    private String address;
    private List<OrderResponse> orders;
    private Instant joinedAt;
}

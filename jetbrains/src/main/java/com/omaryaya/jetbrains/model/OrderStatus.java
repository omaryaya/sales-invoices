package com.omaryaya.jetbrains.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    NEW("NEW"),
    PENDING("PENDING"),
    RETURNED("RETURNED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private String status;

    
    OrderStatus(String status) {
        this.status = status.toUpperCase();
    }

    @Override
    public String toString() {
        return status;
    }

    @JsonCreator
    public static OrderStatus getEnum(String status) {
        return OrderStatus.valueOf(status);
    }
}

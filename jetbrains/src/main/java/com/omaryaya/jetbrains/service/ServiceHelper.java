package com.omaryaya.jetbrains.service;

import java.util.List;

import com.omaryaya.jetbrains.payload.PagedResponse;
import com.omaryaya.jetbrains.util.Mapper;

import org.springframework.data.domain.Page;

public class ServiceHelper<T, Q> {

    public PagedResponse<Q> formatProducts(Page<T> items, Class<Q> qClass) {
        List<Q> responses = items.map(item -> {
            return new Mapper<T, Q>().mapEntityToDto(item, qClass);

        }).getContent();
        

        return new PagedResponse<>(responses, items.getNumber(), items.getSize(),
                items.getTotalElements(), items.getTotalPages(), items.isLast());
    }
    
}

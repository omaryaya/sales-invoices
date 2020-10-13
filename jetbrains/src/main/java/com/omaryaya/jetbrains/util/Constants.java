package com.omaryaya.jetbrains.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface Constants {

    public String DEFAULT_PAGE_NUMBER = "0";
    public String DEFAULT_PAGE_SIZE = "5";

    public int MAX_PAGE_SIZE = 50;

    final Sort DEFAULT_SORT =  Sort.by(Sort.Direction.DESC, "createdAt");

    final Pageable templatePageable = PageRequest.of(0, Integer.parseInt(DEFAULT_PAGE_SIZE));
    
}

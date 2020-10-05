package com.omaryaya.jetbrains.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface Constants {

    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "5";

    int MAX_PAGE_SIZE = 50;

    final Pageable templatePageable = PageRequest.of(0, Integer.parseInt(DEFAULT_PAGE_SIZE));
    
}

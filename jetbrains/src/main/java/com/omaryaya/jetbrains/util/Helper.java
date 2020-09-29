package com.omaryaya.jetbrains.util;

import com.omaryaya.jetbrains.exception.BadRequestException;

public class Helper {

    private static Helper helper = null;

    private Helper(){}
    
    public static Helper getInstance() {
        if(helper == null) helper = new Helper();

        return helper;
    }

    public static void validatePageAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > Constants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + Constants.MAX_PAGE_SIZE);
        }
    }
    
}

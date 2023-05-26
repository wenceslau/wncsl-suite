package com.wncsl.core.domain;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}

package com.fooddelivery.fooddeliveryplatform.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {

        super(message);
    }
}
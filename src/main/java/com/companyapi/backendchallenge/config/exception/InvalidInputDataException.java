package com.companyapi.backendchallenge.config.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException(String message) {
        super(message);
    }
}

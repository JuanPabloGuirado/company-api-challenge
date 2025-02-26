package com.backendchallenge.domain.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException(String message) {
        super(message);
    }
}


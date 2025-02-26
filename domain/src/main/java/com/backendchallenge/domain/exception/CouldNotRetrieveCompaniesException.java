package com.backendchallenge.domain.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class CouldNotRetrieveCompaniesException extends RuntimeException {

    public CouldNotRetrieveCompaniesException(String message) {
        super(message);
    }
}

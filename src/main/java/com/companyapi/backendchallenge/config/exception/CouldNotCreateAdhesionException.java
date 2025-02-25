package com.companyapi.backendchallenge.config.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class CouldNotCreateAdhesionException extends RuntimeException {

    public CouldNotCreateAdhesionException(String message) {
        super(message);
    }
}

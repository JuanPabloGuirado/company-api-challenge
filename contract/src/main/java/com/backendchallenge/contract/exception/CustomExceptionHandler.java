package com.backendchallenge.contract.exception;

import com.backendchallenge.domain.exception.CouldNotCreateAdhesionException;
import com.backendchallenge.domain.exception.CouldNotRetrieveCompaniesException;
import com.backendchallenge.domain.exception.ErrorResponse;
import com.backendchallenge.domain.exception.InvalidInputDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidInputDataException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ErrorResponse.builder()
                        .code(1001)
                        .detail(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            CouldNotCreateAdhesionException.class,
            CouldNotRetrieveCompaniesException.class
    })
    public ResponseEntity<ErrorResponse> handleCouldNotCreateAdhesionException(CouldNotCreateAdhesionException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(ErrorResponse.builder()
                        .code(1002)
                        .detail(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}

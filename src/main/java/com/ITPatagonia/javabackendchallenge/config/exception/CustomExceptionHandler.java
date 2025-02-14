package com.ITPatagonia.javabackendchallenge.config.exception;

import com.ITPatagonia.javabackendchallenge.domain.dto.ErrorResponse;
import com.ITPatagonia.javabackendchallenge.utils.enums.ErrorCode;
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
                                .code(ErrorCode.INVALID_DATA.getCode())
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
                        .code(ErrorCode.INTERNAL_ERROR.getCode())
                        .detail(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}

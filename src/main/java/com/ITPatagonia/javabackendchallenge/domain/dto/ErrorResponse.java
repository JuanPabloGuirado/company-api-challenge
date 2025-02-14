package com.ITPatagonia.javabackendchallenge.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer code;
    private String detail;
}

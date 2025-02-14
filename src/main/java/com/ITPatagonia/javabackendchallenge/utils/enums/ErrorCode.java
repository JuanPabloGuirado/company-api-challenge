package com.ITPatagonia.javabackendchallenge.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_DATA(1001),
    INTERNAL_ERROR(1002);

    private final Integer code;
}

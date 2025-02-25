package com.companyapi.backendchallenge.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Constants {

    CUIT_FORMAT_VALIDATION_REGEX("\\d{2}-\\d{8}-\\d"),
    DATE_FORMAT_YYYYMMDD("\\d{4}-\\d{2}-\\d{2}");

    private final String value;
}

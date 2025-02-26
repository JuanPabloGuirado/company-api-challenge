package com.backendchallenge.contract.validation.aspect;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.exception.InvalidInputDataException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Aspect
@Component
public class ValidationAspect {

    @Before("@annotation(com.backendchallenge.contract.validation.annotation.ValidateInputParams) && args(request, ..)")
    public void validateEmailAndPassword(Company request) {
        log.info(String.format("Validating input data - request body: %s", request));
        if (!Pattern.compile(ValidationConstants.CUIT_FORMAT_VALIDATION_REGEX.getValue())
                .matcher(request.getCuit()).matches()) {
            log.error(String.format("Invalid format for cuit: %s", request.getCuit()));
            throw new InvalidInputDataException("Invalid cuit format. Expected format: XX-XXXXXXXX-X");
        }

        if (StringUtils.isBlank(request.getName())) {
            log.error(String.format("Invalid company name: %s", request.getName()));
            throw new InvalidInputDataException("Company name is required.");
        }

        if (Objects.isNull(request.getRegistrationDate()) || !String.valueOf(request.getRegistrationDate())
                .matches(ValidationConstants.DATE_FORMAT_YYYYMMDD.getValue())) {
            log.error(String.format("Invalid registration date: %s", request.getRegistrationDate()));
            throw new InvalidInputDataException("Registration date is required. Expected format: yyyy-MM-dd");
        }
    }

    @Getter
    @RequiredArgsConstructor
    private enum ValidationConstants {
        CUIT_FORMAT_VALIDATION_REGEX("\\d{2}-\\d{8}-\\d"),
        DATE_FORMAT_YYYYMMDD("\\d{4}-\\d{2}-\\d{2}");

        private final String value;
    }
}

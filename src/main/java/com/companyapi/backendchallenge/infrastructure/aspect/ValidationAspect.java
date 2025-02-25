package com.companyapi.backendchallenge.infrastructure.aspect;

import com.companyapi.backendchallenge.config.exception.InvalidInputDataException;
import com.companyapi.backendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;
import com.companyapi.backendchallenge.utils.enums.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Aspect
@Component
public class ValidationAspect {

    @Before("@annotation(com.companyapi.backendchallenge.domain.validation.ValidateInputParams) && args(request, ..)")
    public void validateEmailAndPassword(CreateCompanyAdhesionRequestDto request) {
        log.info(String.format("Validating input data - request body: %s", request));
        if (!Pattern.compile(Constants.CUIT_FORMAT_VALIDATION_REGEX.getValue()).matcher(request.getCuit()).matches()) {
            log.error(String.format("Invalid format for cuit: %s", request.getCuit()));
            throw new InvalidInputDataException("Invalid cuit format. Expected format: XX-XXXXXXXX-X");
        }

        if (StringUtils.isBlank(request.getCompanyName())) {
            log.error(String.format("Invalid company name: %s", request.getCompanyName()));
            throw new InvalidInputDataException("Company name is required.");
        }

        if (Objects.isNull(request.getRegistrationDate()) || !String.valueOf(request.getRegistrationDate())
                .matches(Constants.DATE_FORMAT_YYYYMMDD.getValue())) {
            log.error(String.format("Invalid registration date: %s", request.getRegistrationDate()));
            throw new InvalidInputDataException("Registration date is required. Expected format: yyyy-MM-dd");
        }
    }
}

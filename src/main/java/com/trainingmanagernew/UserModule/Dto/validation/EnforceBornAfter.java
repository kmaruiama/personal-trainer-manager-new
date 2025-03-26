package com.trainingmanagernew.UserModule.Dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class EnforceBornAfter implements ConstraintValidator<EnforceBornAfterCertainPeriod, LocalDate> {
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        return birthDate.isAfter(LocalDate.of(1924, 12, 31));
    }
}

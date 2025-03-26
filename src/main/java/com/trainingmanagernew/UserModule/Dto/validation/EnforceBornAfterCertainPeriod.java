package com.trainingmanagernew.UserModule.Dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnforceBornAfter.class)
public @interface EnforceBornAfterCertainPeriod {

    String message() default "Nascimento inválido";

    //inutil aqui
    Class<?>[] groups() default {};
    //inutil aqui
    Class<? extends Payload>[] payload() default {};
}

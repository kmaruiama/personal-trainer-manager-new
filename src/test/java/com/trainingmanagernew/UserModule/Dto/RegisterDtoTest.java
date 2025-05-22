package com.trainingmanagernew.UserModule.Dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
@SpringBootTest
class RegisterDtoTest {
    @Autowired
    Validator validator;

    @Test
    void testUsernameValidation() {
        RegisterDto registerDto = new RegisterDto();

        //notNull
        registerDto.setUsername(null);
        Set<ConstraintViolation<RegisterDto>> violations = validator.validateProperty(registerDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotNull.class)));

        //notBlank
        registerDto.setUsername("");
        violations = validator.validateProperty(registerDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotBlank.class)));

        //size (too small)
        registerDto.setUsername("ab");
        violations = validator.validateProperty(registerDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Size.class)));

        //size (too big)
        registerDto.setUsername("pqowierpowqierupqoweirupqwoierupowiqerpoqweirupowieqrupqowierupqwoeirupqweoiru");
        violations = validator.validateProperty(registerDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Size.class)));
    }

    @Test
    void testPasswordValidation() {
        RegisterDto registerDto = new RegisterDto();

        //notNull
        registerDto.setPassword(null);
        Set<ConstraintViolation<RegisterDto>> violations = validator.validateProperty(registerDto, "password");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotNull.class)));

        //notBlank
        registerDto.setPassword("");
        violations = validator.validateProperty(registerDto, "password");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotBlank.class)));
    }

    @Test
    void testEmailValidation() {
        RegisterDto registerDto = new RegisterDto();

        //notNull
        registerDto.setEmail(null);
        Set<ConstraintViolation<RegisterDto>> violations = validator.validateProperty(registerDto, "email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotNull.class)));

        //notBlank
        registerDto.setEmail("");
        violations = validator.validateProperty(registerDto, "email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotBlank.class)));
    }

    @Test
    void testCpfValidation() {
        RegisterDto registerDto = new RegisterDto();

        //notNull
        registerDto.setCpf(null);
        Set<ConstraintViolation<RegisterDto>> violations = validator.validateProperty(registerDto, "cpf");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotNull.class)));

        //notBlank
        registerDto.setCpf("");
        violations = validator.validateProperty(registerDto, "cpf");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotBlank.class)));

        //pattern
        registerDto.setCpf("12345678900");
        violations = validator.validateProperty(registerDto, "cpf");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Pattern.class)));
    }
}



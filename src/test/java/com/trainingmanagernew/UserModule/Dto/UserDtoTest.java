package com.trainingmanagernew.UserModule.Dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Component
class UserDtoTest {

    @Autowired
    Validator validator;

    @Test
    void testUsernameValidation(){
        UserDto userDto = new UserDto();
        // @NotNull
        userDto.setUsername(null);
        Set<ConstraintViolation<UserDto>> violations = validator.validateProperty(userDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotNull.class)));

        // @NotBlank
        userDto.setUsername("");
        violations = validator.validateProperty(userDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotBlank.class)));

        // @Size (too small)
        userDto.setUsername("ab");
        violations = validator.validateProperty(userDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Size.class)));

        // @Size (too big)
        userDto.setUsername("pqowierpowqierupqoweirupqwoierupowiqerpoqweirupowieqrupqowierupqwoeirupqweoiru");
        violations = validator.validateProperty(userDto, "username");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Size.class)));
    }

    @Test
    void testEmailValidation(){
        UserDto userDto = new UserDto();
        userDto.setEmail(null);
        Set<ConstraintViolation<UserDto>> violations = validator.validateProperty(userDto, "email");

        // @NotNull
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotNull.class)));

        // @NotBlank
        userDto.setEmail("");
        violations = validator.validateProperty(userDto, "email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotBlank.class)));


        // @Email
        userDto.setEmail("blalbablablabla.com");
        violations = validator.validateProperty(userDto, "email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Email.class)));
    }

    @Test
    void testPhoneValidation(){
        UserDto userDto = new UserDto();

        // @NotNull
        userDto.setNumber(null);
        Set<ConstraintViolation<UserDto>> violations = validator.validateProperty(userDto, "number");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("number")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotNull.class)));

        // @NotBlank
        userDto.setNumber("");
        violations = validator.validateProperty(userDto, "number");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("number")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.NotBlank.class)));


        // @Pattern (no letters allowed)
        userDto.setNumber("edied");
        violations = validator.validateProperty(userDto, "number");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("number")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Pattern.class)));

        // @Size (minimum of 10)
        userDto.setNumber("1");
        violations = validator.validateProperty(userDto, "number");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("number")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Size.class)));

        // @Size (maximum of 15)
        userDto.setNumber("4567865434567765434567654345671");
        violations = validator.validateProperty(userDto, "number");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("number")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(jakarta.validation.constraints.Size.class)));

    }

}
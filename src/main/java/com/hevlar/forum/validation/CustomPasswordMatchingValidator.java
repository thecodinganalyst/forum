package com.hevlar.forum.validation;

import com.hevlar.forum.controller.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomPasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {

    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserRegistrationDto user = (UserRegistrationDto) value;
        return user.password().equals(user.matchingPassword());
    }
}

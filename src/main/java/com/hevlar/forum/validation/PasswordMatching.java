package com.hevlar.forum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomPasswordMatchingValidator.class)
@Documented
public @interface PasswordMatching {
    String message() default "Passwords doesn't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

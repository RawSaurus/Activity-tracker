package com.miroslav.acitivity_tracker.util.enums;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@NotNull
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClazz();
    String message() default "must be any of enum {enumClazz}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


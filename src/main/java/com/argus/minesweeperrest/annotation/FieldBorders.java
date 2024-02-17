package com.argus.minesweeperrest.annotation;

import com.argus.minesweeperrest.validation.FieldBordersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldBordersValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldBorders {
    String message() default "Value is beyond the game field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

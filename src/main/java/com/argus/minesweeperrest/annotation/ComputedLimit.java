package com.argus.minesweeperrest.annotation;

import com.argus.minesweeperrest.validation.ComputedLimitValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ComputedLimitValidator.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Documented
public @interface ComputedLimit {
}
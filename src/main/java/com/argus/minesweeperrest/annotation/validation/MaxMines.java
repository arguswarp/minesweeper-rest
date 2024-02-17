package com.argus.minesweeperrest.annotation.validation;

import com.argus.minesweeperrest.validation.MaxMinesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MaxMinesValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface MaxMines {
    String message() default "Mines count exceeds limit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

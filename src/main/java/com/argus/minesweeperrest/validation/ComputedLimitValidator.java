package com.argus.minesweeperrest.validation;

import com.argus.minesweeperrest.annotation.ComputedLimit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ComputedLimitValidator implements ConstraintValidator<ComputedLimit, Object[]> {

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
        return false;
    }
}

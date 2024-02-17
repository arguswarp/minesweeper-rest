package com.argus.minesweeperrest.validation;

import com.argus.minesweeperrest.annotation.validation.MaxMines;
import com.argus.minesweeperrest.dto.NewGameRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxMinesValidator implements ConstraintValidator<MaxMines, NewGameRequest> {

    @Override
    public boolean isValid(NewGameRequest request, ConstraintValidatorContext context) {
        int maxMines = request.getWidth() * request.getHeight() - 1;
        if (request.getMinesCount() > maxMines) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Количество мин должно быть меньше количества ячеек поля ("
                    + maxMines
                    + ").").addConstraintViolation();
            return false;
        }
        return true;
    }
}
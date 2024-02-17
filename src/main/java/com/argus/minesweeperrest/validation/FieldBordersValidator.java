package com.argus.minesweeperrest.validation;

import com.argus.minesweeperrest.annotation.FieldBorders;
import com.argus.minesweeperrest.dto.TurnRequest;
import com.argus.minesweeperrest.service.GameService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class FieldBordersValidator implements ConstraintValidator<FieldBorders, TurnRequest> {
    @Autowired
    private GameService gameService;

    @Override
    public boolean isValid(TurnRequest request, ConstraintValidatorContext context) {
        var game = gameService.get(request.getGameID());
        int width = game.getWidth();
        int height = game.getHeight();
        if (request.getCol() > game.getWidth()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Номер столбца не может быть больше ширины игрового поля(" + (width - 1) + ")")
                    .addConstraintViolation();
            return false;
        }
        if (request.getRow() > game.getHeight()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Номер строки не может быть больше высоты игрового поля(" + (height - 1) + ")")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

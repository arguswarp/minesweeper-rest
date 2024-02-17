package com.argus.minesweeperrest.validation;

import com.argus.minesweeperrest.annotation.validation.FieldBorders;
import com.argus.minesweeperrest.dto.TurnRequest;
import com.argus.minesweeperrest.repository.GameRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class FieldBordersValidator implements ConstraintValidator<FieldBorders, TurnRequest> {
    @Autowired
    private GameRepository gameRepository;

    @Override
    public boolean isValid(TurnRequest request, ConstraintValidatorContext context) {
        var gameOptional = gameRepository.findByGameID(request.getGameID());
        if (gameOptional.isPresent()) {
            var game = gameOptional.get();
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
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Игры с таким uuid не существует: " + request.getGameID().toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

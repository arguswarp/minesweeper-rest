package com.argus.minesweeperrest.util;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.model.Cell;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GameUtil {

    public static String countMinesAround(Cell cell) {
        //считаем количество мин вокруг
        return "";
    }

    public static long countMines(Game game) {
        return Arrays.stream(game.getField())
                .flatMap(cells -> Arrays.stream(cells).filter(Cell::isMine))
                .count();
    }

    public static String[][] convertToStringArray(Game game) {
        Cell[][] field = game.getField();
        String[][] newField = new String[game.getWidth()][game.getHeight()];
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                newField[i][j] = field[i][j].getValue();
            }
        }
        return newField;
    }
}

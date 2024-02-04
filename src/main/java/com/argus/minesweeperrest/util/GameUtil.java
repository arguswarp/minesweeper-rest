package com.argus.minesweeperrest.util;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.model.Cell;

import java.util.Arrays;
import java.util.function.Consumer;

public class GameUtil {

    public static long countMines(Game game) {
        return Arrays.stream(game.getFieldSource())
                .flatMap(cells -> Arrays.stream(cells).filter(Cell::isMine))
                .count();
    }

    public static String[][] convertToStringArray(Game game) {
        Cell[][] field = game.getField();
        String[][] newField = new String[game.getHeight()][game.getWidth()];
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                newField[i][j] = field[i][j].getValue();
            }
        }
        return newField;
    }

    public static String[][] convertToStringArray(Cell[][] field) {

        String[][] newField = new String[field.length][field[0].length];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                newField[i][j] = field[i][j].getValue();
            }
        }
        return newField;
    }

    public static Cell[][] doForEachCell(Cell[][] field, Consumer<Cell> consumer) {
        Arrays.stream(field).forEach(cells -> Arrays.stream(cells)
                .forEach(consumer));
        return field;
    }

    public static String fieldToString(String[][] array) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        Arrays.stream(array).forEach(strings -> result.append("|")
                .append(String.join("|", strings))
                .append("|")
                .append("\n"));
        return result.toString();
    }
}

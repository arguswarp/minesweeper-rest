package com.argus.minesweeperrest.model;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.util.GameUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
class FieldGeneratorTest {

    private final FieldGenerator fieldGenerator = new FieldGenerator();

    @Test
    public void WhenMinesPlaced_MinesCountIs_10() {
        int width = 5;
        int height = 5;
        int mines = 10;

        Game game = Game.builder()
                .width(width)
                .height(height)
                .minesCount(mines)
                .field(new Cell[height][width])
                .build();

        Cell[][] field = fieldGenerator.initializeField(game);
        field = fieldGenerator.placeMines(game.getMinesCount(), field);
        game.setField(field);
        log.info("\n" + fieldToString(GameUtil.convertToStringArray(game)));

        Assertions.assertNotNull(game);
        Assertions.assertEquals(10, GameUtil.countMines(game));
    }

    @Test
    void WhenValuesCalculated_FieldsAreEquals() {
        int width = 5;
        int height = 5;
        int mines = 11;

        Cell [][] field;

        Game game = Game.builder()
                .width(width)
                .height(height)
                .minesCount(mines)
                .build();
        field = fieldGenerator.initializeField(game);
        game.setField(field);

        field[0][1] = Cell.builder()
                .x(1)
                .y(0)
                .isMine(true)
                .value("X")
                .build();
        field[1][2] = Cell.builder()
                .x(2)
                .y(1)
                .isMine(true)
                .value("X")
                .build();
        field[1][0] = Cell.builder()
                .x(0)
                .y(1)
                .isMine(true)
                .value("X")
                .build();
        for (int i = 2; i < 5; i++) {
            for (int j = 1; j < 4; j++) {
                if (i==3 && j==2) continue;
                field[i][j] = Cell.builder()
                        .x(j)
                        .y(i)
                        .isMine(true)
                        .value("X")
                        .build();
            }
        }

        log.info("\n" + fieldToString(GameUtil.convertToStringArray(game)));
        field = fieldGenerator.calculateValues(field);
        game.setField(field);
        Assertions.assertNotNull(game);
        log.info("\n" + fieldToString(GameUtil.convertToStringArray(game)));
    }

    private String fieldToString(String[][] array) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(array).forEach(strings -> result.append("|")
                .append(String.join("|", strings))
                .append("|")
                .append("\n"));
        return result.toString();
    }
}
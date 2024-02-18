package com.argus.minesweeperrest.model;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.util.GameUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
class FieldGeneratorTest {

    private final FieldGenerator fieldGenerator = new FieldGenerator();

    private Game game;

    @BeforeEach
    void prepareGame() {
        int width = 10;
        int height = 10;
        int mines = 10;

        game = Game.builder()
                .width(width)
                .height(height)
                .minesCount(mines)
                .build();
        game.setFieldSource(fieldGenerator.initializeField(game));
    }

    @Test
    void WhenMinesPlaced_MinesCountIs_10() {
        Cell[][] field = game.getFieldSource();
        field = fieldGenerator.placeMines(game.getMinesCount(), field);
        game.setFieldSource(field);
        log.info("\n" + GameUtil.fieldToString(GameUtil.convertToStringArray(game)));

        Assertions.assertNotNull(game);
        Assertions.assertEquals(10, GameUtil.countMines(game));
    }

    @Test
    void WhenValuesCalculated_FieldsAreEquals() {
        Cell[][] field = game.getFieldSource();
        field = fieldGenerator.placeMines(game.getMinesCount(), field);
        game.setFieldSource(field);
        log.info("\n" + GameUtil.fieldToString(GameUtil.convertToStringArray(game)));

        field = fieldGenerator.calculateValues(field);
        game.setFieldSource(field);

        Assertions.assertNotNull(game);
        log.info("\n" + GameUtil.fieldToString(GameUtil.convertToStringArray(game)));
    }
}
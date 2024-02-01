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
                .field(new Cell[width][height])
                .build();

        Cell[][] initializedField = fieldGenerator.initializeField(game);
        game.setField(initializedField);

        game = fieldGenerator.placeMines(game);
        log.info(Arrays.deepToString(GameUtil.convertToStringArray(game)));

        Assertions.assertNotNull(game);
        Assertions.assertEquals(10, GameUtil.countMines(game));
    }
}
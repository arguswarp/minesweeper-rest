package com.argus.minesweeperrest.service;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.exception.GameErrorException;
import com.argus.minesweeperrest.model.Cell;
import com.argus.minesweeperrest.model.FieldGenerator;
import com.argus.minesweeperrest.model.TurnHelper;
import com.argus.minesweeperrest.model.enums.CellValues;
import com.argus.minesweeperrest.repository.GameRepository;
import com.argus.minesweeperrest.util.GameUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@Slf4j
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    private final TurnHelper turnHelper = new TurnHelper();

    private final FieldGenerator fieldGenerator = new FieldGenerator();

    private Game game;

    @BeforeEach
    void prepareGame() {
        gameService = new GameService(gameRepository, fieldGenerator, turnHelper);
        game = Game.builder()
                .gameID(UUID.randomUUID())
                .width(6)
                .height(6)
                .minesCount(5)
                .completed(false)
                .build();
        Cell[][] fieldSource = fieldGenerator.initializeField(game);
        Cell[][] fieldTarget = fieldGenerator.initializeField(game);
        //manually place mines
        setAsMine(0, 0, fieldSource);
        setAsMine(1, 1, fieldSource);
        setAsMine(2, 4, fieldSource);
        setAsMine(3, 3, fieldSource);
        setAsMine(4, 3, fieldSource);
        fieldSource = fieldGenerator.calculateValues(fieldSource);
        game.setFieldSource(fieldSource);
        game.setField(fieldTarget);
        Mockito.when(gameRepository.save(game)).thenReturn(game);
        log.info(GameUtil.fieldToString(GameUtil.convertToStringArray(game.getFieldSource())));
    }

    private void setAsMine(int col, int row, Cell[][] field) {
        Cell cell = field[row][col];
        cell.setMine(true);
        cell.setValue(CellValues.MINE_LOST.getValue());
    }

    @Test
    void WhenChooseCellWithMine_ThenGameCompleted() {
        gameService.doTurn(0, 0, game);

        Assertions.assertTrue(game.getCompleted());
        Assertions.assertEquals(CellValues.MINE_LOST.getValue(), GameUtil.getCellValue(0, 0, game.getField()));
        log.info(GameUtil.fieldToString(GameUtil.convertToStringArray(game.getField())));
    }

    @Test
    void WhenChooseCellWithoutMine_ThenValueRevealed() {
        int col = 0;
        int row = 1;
        gameService.doTurn(col, row, game);

        Assertions.assertEquals("2", GameUtil.getCellValue(col, row, game.getField()));
        log.info(GameUtil.fieldToString(GameUtil.convertToStringArray(game.getField())));
    }

    @Test
    void WhenChooseCellWithValueZero_ThenCellsAroundRevealed() {
        int col = 5;
        int row = 0;
        gameService.doTurn(col, row, game);

        Assertions.assertEquals("0", GameUtil.getCellValue(col, row, game.getField()));
        Assertions.assertEquals("0", GameUtil.getCellValue(5, 1, game.getField()));
        Assertions.assertEquals("1", GameUtil.getCellValue(5, 2, game.getField()));
        Assertions.assertEquals("0", GameUtil.getCellValue(4, 0, game.getField()));
        log.info(GameUtil.fieldToString(GameUtil.convertToStringArray(game.getField())));
    }

    @Test
    void WhenWinningTurnsMade_ThenGameCompleted() {
        gameService.doTurn(5, 0, game);
        gameService.doTurn(0, 5, game);
        gameService.doTurn(5, 5, game);
        gameService.doTurn(0, 1, game);
        gameService.doTurn(1, 0, game);
        gameService.doTurn(2, 3, game);
        gameService.doTurn(2, 5, game);
        gameService.doTurn(5, 3, game);

        Assertions.assertTrue(game.getCompleted());
        Assertions.assertEquals(CellValues.MINE_WIN.getValue(), GameUtil.getCellValue(0, 0, game.getField()));
        log.info(GameUtil.fieldToString(GameUtil.convertToStringArray(game.getField())));
    }

    @Test
    void WhenChooseRevealedCell_ThenGameErrorExceptionThrown() {
        int col = 0;
        int row = 1;
        gameService.doTurn(col, row, game);
        Assertions.assertThrows(GameErrorException.class, () -> gameService.doTurn(col, row, game));
    }
}
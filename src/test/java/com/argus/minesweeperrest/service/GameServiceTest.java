package com.argus.minesweeperrest.service;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.model.Cell;
import com.argus.minesweeperrest.model.FieldGenerator;
import com.argus.minesweeperrest.repository.GameRepository;
import com.argus.minesweeperrest.util.GameUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
@Slf4j
class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private GameRepository gameRepository;

    private final FieldGenerator fieldGenerator = new FieldGenerator();

    @Test
    void WhenDoTurn_SimulateGame() {
        Game game = Game.builder()
                .width(10)
                .height(10)
                .minesCount(10)
                .completed(false)
                .build();
        Cell[][] fieldSource = fieldGenerator.initializeField(game);
        Cell[][] fieldTarget = fieldGenerator.initializeField(game);
        fieldSource = fieldGenerator.placeMines(game.getMinesCount(), fieldSource);
        fieldSource = fieldGenerator.calculateValues(fieldSource);
        game.setField(fieldTarget);
        game.setFieldSource(fieldSource);

        Mockito.when(gameRepository.save(game)).thenReturn(game);
        Random random = new Random();
        int i = 1;
        log.info(GameUtil.fieldToString(GameUtil.convertToStringArray(game.getFieldSource())));
        while (!game.getCompleted()) {
            int col = random.nextInt(0, 10);
            int row = random.nextInt(0, 10);
            gameService.doTurn(col, row, game);
            log.info("Turn " + i++
                    + " x= " + col + " y= " + row
                    + GameUtil.fieldToString(GameUtil.convertToStringArray(game)));
        }
    }
}
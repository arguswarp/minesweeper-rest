package com.argus.minesweeperrest.service;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.exception.ErrorResponseException;
import com.argus.minesweeperrest.model.Cell;
import com.argus.minesweeperrest.model.FieldGenerator;
import com.argus.minesweeperrest.model.TurnHelper;
import com.argus.minesweeperrest.model.enums.CellValues;
import com.argus.minesweeperrest.repository.GameRepository;
import com.argus.minesweeperrest.util.GameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
public class GameService {

    private final GameRepository gameRepository;

    private final FieldGenerator fieldGenerator;

    private final TurnHelper turnHelper;

    @Autowired
    public GameService(GameRepository gameRepository, FieldGenerator fieldGenerator, TurnHelper turnHelper) {
        this.gameRepository = gameRepository;
        this.fieldGenerator = fieldGenerator;
        this.turnHelper = turnHelper;
    }

    public Game newGame(Game game) {
        Cell[][] fieldSource = fieldGenerator.initializeField(game);
        Cell[][] field = fieldGenerator.initializeField(game);

        game.setField(field);
        game.setCompleted(false);
        fieldSource = fieldGenerator.placeMines(game.getMinesCount(), fieldSource);
        fieldSource = fieldGenerator.calculateValues(fieldSource);
        game.setFieldSource(fieldSource);
        gameRepository.save(game);
        return game;
    }

    public Game doTurn(int col, int row, Game game) {
        Cell[][] fieldTarget = game.getField();
        Cell cellTarget = game.getField()[row][col];

        if (cellTarget.isRevealed()) {
            throw new ErrorResponseException("Эта ячейка уже открыта");
        }

        Cell[][] fieldSource = game.getFieldSource();
        Cell cellSource = fieldSource[row][col];

        if (cellSource.isMine()) {
            game.setField(fieldSource);
            game.setCompleted(true);
            log.info("Game with id: " + game.getGameID().toString() + " target cell is mine");
            return gameRepository.save(game);
        }

        fieldTarget = turnHelper.revealCells(cellTarget, cellSource, fieldTarget, fieldSource);
        game.setField(fieldTarget);

        long cellsRevealed = Arrays.stream(fieldTarget)
                .flatMap(cells -> Arrays.stream(cells).filter(Cell::isRevealed))
                .count();
        int cellsToWin = game.getHeight() * game.getWidth() - game.getMinesCount();
        if (cellsRevealed >= cellsToWin) {
            game.setCompleted(true);
            GameUtil.doForEachCell(fieldSource, cell -> {
                if (cell.isMine()) {
                    cell.setValue(CellValues.MINE_WIN.getValues());
                }
            });
            game.setField(fieldSource);
            log.info("Game with id: " + game.getGameID().toString() + " all cells revealed");
            return gameRepository.save(game);
        }
        return gameRepository.save(game);
    }

    public Game get(UUID uuid) {
        return gameRepository.findByGameID(uuid)
                .orElseThrow(() -> new ErrorResponseException("Игры с таким UUID не существует"));
    }
}

package com.argus.minesweeperrest.service;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.exception.ErrorResponseException;
import com.argus.minesweeperrest.model.Cell;
import com.argus.minesweeperrest.model.FieldGenerator;
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
    //TODO cut all turn logic from service to helper
//    private final TurnHelper turnHelper;

    @Autowired
    public GameService(GameRepository gameRepository, FieldGenerator fieldGenerator) {
        this.gameRepository = gameRepository;
        this.fieldGenerator = fieldGenerator;
    }

    public Game generateField(Game game) {
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
        //найти эту ячейку в завершенном поле

        Cell[][] field = game.getField();
        Cell cellTarget = field[row][col];
        //если не пустая - то кидаем исключение
        if (cellTarget.isRevealed()) {
            throw new ErrorResponseException("Эта ячейка уже открыта");
        }
        Cell[][] fieldSource = game.getFieldSource();
        Cell cellSource = fieldSource[row][col];
        //если бомба - ставим что игра завершена, в игровое поле сохраняем завершенное поле, возвращаем игру
        if (cellSource.isMine()) {
            game.setCompleted(true);
            game.setField(fieldSource);
            return gameRepository.save(game);
            //иначе - проверяем значение в ячейке
            //если значение > 0 - записываем в игровое поле, сохраняем игру, возвращаем игру
        } else if (Integer.parseInt(cellSource.getValue()) > 0) {
            cellTarget.setValue(cellSource.getValue());
            cellTarget.setRevealed(true);
            game = gameRepository.save(game);
        } else {
            /* иначе если значение == 0 - записываем это значение в ячейку, обходим соседние,
        если они тоже 0, продолжаем обход пока не встретим не 0, их тоже записываем и останавливаемся,
        возвращаем игру
         */

            traverseAroundCell(cellTarget, field, fieldSource);
            game = gameRepository.save(game);
        }
        //условие победы - открыты все ячейки не с минами, т.е. нужно их считать
        long cellsRevealed = Arrays.stream(field).flatMap(cells -> Arrays.stream(cells).filter(Cell::isRevealed)).count();
        int cellsToWin = game.getHeight() * game.getWidth() - game.getMinesCount();
        if (cellsRevealed >= cellsToWin) {
            game.setCompleted(true);
                GameUtil.doForEachCell(fieldSource, cell -> {
                    if (cell.isMine()) {
                        cell.setValue("M");
                    }
                });
                game.setField(fieldSource);
            return gameRepository.save(game);
        }
        return game;
    }

    private void traverseAroundCell(Cell cell, Cell[][] target, Cell[][] source) {
        if (cell.isRevealed()) return;

        int row = cell.getY();
        int col = cell.getX();

        cell.setValue(source[row][col].getValue());
        cell.setRevealed(true);

        setValueForCellAround(col - 1, row - 1, target, source);
        setValueForCellAround(col, row - 1, target, source);
        setValueForCellAround(col + 1, row - 1, target, source);

        setValueForCellAround(col - 1, row, target, source);
        setValueForCellAround(col + 1, row, target, source);

        setValueForCellAround(col - 1, row + 1, target, source);
        setValueForCellAround(col, row + 1, target, source);
        setValueForCellAround(col + 1, row + 1, target, source);
    }

    private void setValueForCellAround(int x, int y, Cell[][] target, Cell[][] source) {
        if ((y >= 0 && y < source.length && x >= 0 && x < source[0].length)) {
            target[y][x].setValue(source[y][x].getValue());
            target[y][x].setRevealed(true);
            if (source[y][x].getValue().equals("0")) {
                traverseAroundCell(source[y][x], target, source);
            }
        }
    }

    public Game get(UUID uuid) {
        return gameRepository.findByGameID(uuid)
                .orElseThrow(()-> new ErrorResponseException("Игры с таким UUID не существует"));
    }
}

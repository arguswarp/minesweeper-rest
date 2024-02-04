package com.argus.minesweeperrest.model;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.util.GameUtil;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@NoArgsConstructor
@Component
public class FieldGenerator {
    private final Random random = new Random();

    public Cell[][] placeMines(int minesCount, Cell[][] field) {
        int width = field[0].length;
        int height = field.length;
        int t = 0;
        while (t != minesCount) {
            int x = random.nextInt(0, width);
            int y = random.nextInt(0, height);
            Cell cell = field[y][x];
            if (!cell.getValue().equals("X")) {
                cell.setValue("X");
                cell.setMine(true);
                t++;
            }
        }
        return field;
    }

    public Cell[][] initializeField(Game game) {
        Cell[][] field = new Cell[game.getHeight()][game.getWidth()];
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                field[i][j] = Cell.builder()
                        .isMine(false)
                        .value(" ")
                        .x(j)
                        .y(i)
                        .build();
            }
        }
        return field;
    }

    public Cell[][] calculateValues(Cell[][] field) {
        return GameUtil.doForEachCell(field, cell -> {
            if (!cell.isMine()) {
                cell.setValue(countMinesAroundCell(field, cell));
            }
        });
    }

    private String countMinesAroundCell(Cell[][] field, Cell cell) {
        int count = 0;
        int x = cell.getX();
        int y = cell.getY();

        count += incrementCount(field, x - 1, y - 1);
        count += incrementCount(field, x, y - 1);
        count += incrementCount(field, x + 1, y - 1);

        count += incrementCount(field, x - 1, y);
        count += incrementCount(field, x + 1, y);

        count += incrementCount(field, x - 1, y + 1);
        count += incrementCount(field, x, y + 1);
        count += incrementCount(field, x + 1, y + 1);

        return String.valueOf(count);
    }

    private static int incrementCount(Cell[][] field, int x, int y) {
        return (y >= 0 && y < field.length && x >= 0 && x < field[0].length) && field[y][x].isMine() ? 1 : 0;
    }
}

package com.argus.minesweeperrest.model;

import com.argus.minesweeperrest.entity.Game;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@NoArgsConstructor
@Component
public class FieldGenerator {
    private final Random random = new Random();

    public Game placeMines(Game game) {
        int minesCount = game.getMinesCount();
        Cell[][] field = game.getField();
        int width = game.getWidth();
        int height = game.getHeight();
        int t = 0;
        while (t != minesCount) {
            int x = random.nextInt(0, width);
            int y = random.nextInt(0, height);
            Cell cell = field[x][y];
            if (!cell.getValue().equals("X")) {
                cell.setValue("X");
                cell.setMine(true);
                t++;
            }
        }
        return game;
    }

    public Cell[][] initializeField(Game game) {
        Cell[][] field = new Cell[game.getWidth()][game.getHeight()];
        Arrays.stream(field).forEach(cells -> {
            for (int i = 0; i < cells.length; i++) {
                cells[i]= Cell.builder()
                        .isMine(false)
                        .value(" ")
                        .build();
            }
        });
        return field;
    }
}

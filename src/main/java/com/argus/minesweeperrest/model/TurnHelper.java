package com.argus.minesweeperrest.model;

import org.springframework.stereotype.Component;

@Component
public class TurnHelper {

    public Cell[][] revealCells(Cell cellTarget, Cell cellSource, Cell[][] fieldTarget, Cell[][] fieldSource) {
        if (Integer.parseInt(cellSource.getValue()) > 0) {
            cellTarget.setValue(cellSource.getValue());
            cellTarget.setRevealed(true);
        } else {
            traverseAroundCell(cellTarget, fieldTarget, fieldSource);
        }
        return fieldTarget;
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
}

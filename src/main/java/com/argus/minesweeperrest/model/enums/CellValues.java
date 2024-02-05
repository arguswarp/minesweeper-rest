package com.argus.minesweeperrest.model.enums;

public enum CellValues {
    BLANK(" "),
    MINE_LOST("X"),
    MINE_WIN("M");

    private final String values;

    CellValues(String values) {
        this.values = values;
    }

    public String getValues() {
        return values;
    }
}

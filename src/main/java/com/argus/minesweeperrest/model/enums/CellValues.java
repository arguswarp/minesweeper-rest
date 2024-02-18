package com.argus.minesweeperrest.model.enums;

public enum CellValues {
    BLANK(" "),
    MINE_LOST("X"),
    MINE_WIN("M");

    private final String value;

    CellValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

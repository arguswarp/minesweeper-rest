package com.argus.minesweeperrest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cell {

    private String value;

    private boolean isMine;
}

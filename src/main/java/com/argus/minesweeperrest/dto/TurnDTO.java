package com.argus.minesweeperrest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TurnDTO {
    private String gameID;

    private Integer col;

    private Integer row;
}

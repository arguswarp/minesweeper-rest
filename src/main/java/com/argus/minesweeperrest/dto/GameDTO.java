package com.argus.minesweeperrest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GameDTO {
    private UUID gameID;
    @Min(1)
    @Max(30)
    private int width;
    @Min(1)
    @Max(30)
    private int height;
    //валидация от 1 до width * height - 1
    private int minesCount;

    private Boolean completed;

    private String[][] field;
}

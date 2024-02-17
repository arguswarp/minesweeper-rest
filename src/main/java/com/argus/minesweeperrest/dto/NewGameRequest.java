package com.argus.minesweeperrest.dto;

import com.argus.minesweeperrest.annotation.validation.MaxMines;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@MaxMines
public class NewGameRequest {
    @Min(value = 2, message = "Ширина должна быть больше 2 ячеек")
    @Max(value = 30, message = "Ширина должна быть не более 30 ячеек")
    private Integer width;
    @Min(value = 2, message = "Высота должна быть больше 2 ячеек")
    @Max(value = 30, message = "Высота должна быть не более 30 ячеек")
    private Integer height;

    @Min(value = 1, message = "Мин должно быть больше 0")
    @JsonProperty("mines_count")
    private Integer minesCount;
}

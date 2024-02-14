package com.argus.minesweeperrest.dto;

import com.argus.minesweeperrest.annotation.ComputedLimit;
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
public class NewGameRequest {
    @Min(value = 2, message = "Ширина должна быть больше 2 ячеек")
    @Max(value = 30, message = "Ширина должна быть не более 30 ячеек")
    private Integer width;
    @Min(value = 2, message = "Высота должна быть больше 2 ячеек")
    @Max(value = 30, message = "Высота должна быть не более 30 ячеек")
    private Integer height;
    @ComputedLimit
    @JsonProperty("mines_count")
    private Integer minesCount;
}

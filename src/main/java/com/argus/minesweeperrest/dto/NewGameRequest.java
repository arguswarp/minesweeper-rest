package com.argus.minesweeperrest.dto;

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
    @Min(2)
    @Max(30)
    private Integer width;
    @Min(2)
    @Max(30)
    private Integer height;
    //валидация от 1 до width * height - 1
    @JsonProperty("mines_count")
    private Integer minesCount;
}

package com.argus.minesweeperrest.dto;

import com.argus.minesweeperrest.annotation.FieldBorders;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@FieldBorders
public class TurnRequest {

    @JsonProperty(value = "game_id")
    private UUID gameID;
    @Min(value = 1, message = "Номер столбца не может быть меньше 0")
    private Integer col;
    @Min(value = 1, message = "Номер строки не может быть меньше 0")
    private Integer row;
}

package com.argus.minesweeperrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
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
public class GameDTO {
    @JsonProperty(value = "game_id")
    private UUID gameID;
    @Min(1)
    @Max(30)
    @JsonProperty(value = "width")
    private Integer width;
    @Min(1)
    @Max(30)
    @JsonProperty(value = "height")
    private Integer height;
    @JsonProperty(value = "mines_count")
    private Integer minesCount;
    @JsonProperty(value = "completed")
    private Boolean completed;
    @JsonProperty(value = "field")
    private String[][] field;
}

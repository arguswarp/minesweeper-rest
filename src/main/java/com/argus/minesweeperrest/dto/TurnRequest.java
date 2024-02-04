package com.argus.minesweeperrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class TurnRequest {

    @JsonProperty(value = "game_id")
    private UUID gameID;

    private Integer col;

    private Integer row;
}

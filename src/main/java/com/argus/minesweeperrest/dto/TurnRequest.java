package com.argus.minesweeperrest.dto;

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
    private UUID gameID;

    private Integer col;

    private Integer row;
}

package com.argus.minesweeperrest.model;

import com.argus.minesweeperrest.dto.GameDTO;
import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.util.GameUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Slf4j
class GameToGameDTOTest {
    private final ModelMapper modelMapper = new ModelMapper();

    private final FieldGenerator fieldGenerator = new FieldGenerator();

    @Test
    void whenMappedFromGame_thenCorrectGameDTO() {
        Game game = Game.builder()
                .gameID(UUID.randomUUID())
                .width(10)
                .height(10)
                .minesCount(10)
                .build();
        Cell[][] field;
        field = fieldGenerator.initializeField(game);
        field = fieldGenerator.placeMines(game.getMinesCount(), field);
        field = fieldGenerator.calculateValues(field);
        game.setFieldSource(field);

        GameDTO gameDTO = convertToGameDTO(game);

        log.info(gameDTO.toString());


    }

    private GameDTO convertToGameDTO(Game game) {
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setField(GameUtil.convertToStringArray(game));
        return gameDTO;
    }
}

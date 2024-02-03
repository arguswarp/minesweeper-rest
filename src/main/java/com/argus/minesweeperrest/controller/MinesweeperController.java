package com.argus.minesweeperrest.controller;

import com.argus.minesweeperrest.dto.GameDTO;
import com.argus.minesweeperrest.dto.NewGameRequest;
import com.argus.minesweeperrest.dto.TurnRequest;
import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.model.Cell;
import com.argus.minesweeperrest.service.GameService;
import com.argus.minesweeperrest.util.GameUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/game")
public class MinesweeperController {
    private final GameService gameService;
    private final ModelMapper modelMapper;

    @Autowired
    public MinesweeperController(GameService gameService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/new")
    public GameDTO newGame(@Valid @RequestBody NewGameRequest request) {
        log.info(request.toString());
        //инициализация игры
        Game game = convertToGame(request);
        game.setCompleted(false);
        Game persistentGame = gameService.generateField(game);
        log.info(game.toString());
        persistentGame = gameService.save(persistentGame);
        //TODO service must provide game with blank field, this is temporary
        persistentGame.setField(GameUtil.doForEachCell(persistentGame.getField(), cell -> {

            cell.setValue(" ");
        }));
        //валидация + респонс с ошибкой
        //генерация поля
        log.info(persistentGame.getGameID().toString() + " " + "game initialized");
        return convertToGameDTO(persistentGame);
    }

    private Game convertToGame(NewGameRequest request) {
        return modelMapper.map(request, Game.class);
    }

    private GameDTO convertToGameDTO(Game game) {
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setField(GameUtil.convertToStringArray(game));
        return gameDTO;
    }

    @PostMapping("/turn")
    public Game doTurn(@Valid @RequestBody TurnRequest turnRequest) {
        Game game = gameService.get(turnRequest.getGameID());
        //проверяем позицию на поле

        return gameService.save(game);
    }
}

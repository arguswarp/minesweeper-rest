package com.argus.minesweeperrest.controller;

import com.argus.minesweeperrest.dto.GameDTO;
import com.argus.minesweeperrest.dto.NewGameRequest;
import com.argus.minesweeperrest.dto.TurnRequest;
import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.exception.ErrorResponseException;
import com.argus.minesweeperrest.service.GameService;
import com.argus.minesweeperrest.util.GameUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @CrossOrigin
    @PostMapping("/new")
    public GameDTO newGame(@Valid @RequestBody NewGameRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO proper message for field errors
            throw new ErrorResponseException(bindingResult.getFieldErrors().toString());
        }
        log.debug(request.toString());
        Game game = convertToGame(request);
        Game blankGame = gameService.generateField(game);
        log.info("Game with id: " + blankGame.getGameID().toString() + " successfully initialized");
        return convertToGameDTO(blankGame);
    }

    private Game convertToGame(NewGameRequest request) {
        return modelMapper.map(request, Game.class);
    }

    private GameDTO convertToGameDTO(Game game) {
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setField(GameUtil.convertToStringArray(game));
        return gameDTO;
    }

    @CrossOrigin
    @PostMapping("/turn")
    public Game doTurn(@Valid @RequestBody TurnRequest turnRequest) {
        Game game = gameService.get(turnRequest.getGameID());
        //проверяем позицию на поле

        return gameService.save(game);
    }

    @ExceptionHandler({ErrorResponseException.class})
    private ResponseEntity<Object> handleException(ErrorResponseException exception) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", exception.getMessage()));
    }
}

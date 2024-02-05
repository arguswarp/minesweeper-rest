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
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

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
        int minesCount = request.getMinesCount();
        int maxMines = request.getHeight() * request.getWidth() - 1;
        if (minesCount > maxMines || minesCount <= 0) {
            bindingResult.addError(new FieldError(request.getClass().getName(), "minesCount",
                    "Мин должно быть больше 0 и меньше количества ячеек (" + maxMines + ")"));
        }
        checkValidationErrors(bindingResult);
        log.debug(request.toString());
        Game game = convertToGame(request);
        Game blankGame = gameService.newGame(game);
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
    public GameDTO doTurn(@Valid @RequestBody TurnRequest request, BindingResult bindingResult) {
        int col = request.getCol();
        int row = request.getRow();
        Game game = gameService.get(request.getGameID());
        int width = game.getWidth();
        int height = game.getHeight();
        if (col > width - 1 || col < 0) {
            bindingResult.addError(new FieldError(request.getClass().getName(), "col",
                    "Номер столбца не может быть меньше 0 или больше ширины игрового поля(" + (width - 1) + ")"));
        }
        if (row > height - 1 || row < 0) {
            bindingResult.addError(new FieldError(request.getClass().getName(), "col",
                    "Номер строки не может быть меньше 0 или больше высоты игрового поля(" + (height - 1) + ")"));
        }
        checkValidationErrors(bindingResult);
        if (game.getCompleted()) {
            throw new ErrorResponseException("Игра уже завершена");
        }
        game = gameService.doTurn(col, row, game);
        log.info("Game with id: " + game.getGameID().toString() + " new turn;" + "completion status: " + game.getCompleted());
        return convertToGameDTO(game);
    }

    private void checkValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ErrorResponseException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". ")));
        }
    }

    @ExceptionHandler({ErrorResponseException.class})
    private ResponseEntity<Object> handleException(ErrorResponseException exception) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", exception.getMessage()));
    }
}

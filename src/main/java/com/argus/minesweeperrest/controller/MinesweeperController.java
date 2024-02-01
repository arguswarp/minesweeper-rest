package com.argus.minesweeperrest.controller;

import com.argus.minesweeperrest.dto.GameDTO;
import com.argus.minesweeperrest.dto.TurnDTO;
import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.service.GameService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class MinesweeperController {
    @Autowired
    private final GameService gameService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/new")
    public Game newGame(@Valid @RequestBody GameDTO gameDTO) {
        //инициализация игры
        Game game = convertToGame(gameDTO);
        //валидация + респонс с ошибкой
        //генерация поля

        return gameService.generateField(game);
    }

    private Game convertToGame(GameDTO gameDTO){
        return modelMapper.map(gameDTO, Game.class);
    }

    @PostMapping("/turn")
    public Game doTurn(@Valid @RequestBody TurnDTO turnDTO) {
        Game game = gameService.get(turnDTO.getGameID());
        //проверяем позицию на поле

        return gameService.save(game);
    }
}

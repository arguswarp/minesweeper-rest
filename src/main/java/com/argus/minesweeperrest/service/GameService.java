package com.argus.minesweeperrest.service;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.model.Cell;
import com.argus.minesweeperrest.model.FieldGenerator;
import com.argus.minesweeperrest.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final FieldGenerator fieldGenerator;

    @Autowired
    public GameService(GameRepository gameRepository, FieldGenerator fieldGenerator) {
        this.gameRepository = gameRepository;
        this.fieldGenerator = fieldGenerator;
    }

    public Game generateField(Game game) {
        Cell[][] field = fieldGenerator.initializeField(game);
        field = fieldGenerator.placeMines(game.getMinesCount(), field);
        field = fieldGenerator.calculateValues(field);
        game.setField(field);
        return gameRepository.save(game);
    }

    public Game get(UUID uuid) {
        return gameRepository.findByGameID(uuid);
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }
}

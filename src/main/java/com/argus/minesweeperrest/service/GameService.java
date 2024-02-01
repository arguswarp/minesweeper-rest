package com.argus.minesweeperrest.service;

import com.argus.minesweeperrest.entity.Game;
import com.argus.minesweeperrest.model.Cell;
import com.argus.minesweeperrest.model.FieldGenerator;
import com.argus.minesweeperrest.repository.GameRepository;
import com.argus.minesweeperrest.util.GameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        game.setField(field);
        return gameRepository.save(game);
    }

    public Game get(String hashID) {
        //из хэша в лонг преобразуем
        long id = Long.parseLong(hashID);
        //добавить эксепшн для базы данных
        return gameRepository.findById(id).orElse(null);
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }
}

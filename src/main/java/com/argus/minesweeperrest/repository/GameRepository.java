package com.argus.minesweeperrest.repository;

import com.argus.minesweeperrest.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByGameID(UUID uuid);
}

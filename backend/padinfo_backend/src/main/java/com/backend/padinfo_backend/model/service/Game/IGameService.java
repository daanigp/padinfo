package com.backend.padinfo_backend.model.service.Game;

import com.backend.padinfo_backend.model.entity.Game;

import java.util.List;

public interface IGameService {
    List<Game> findAll();
    Game findById(long id);
    Game createGame(Game game);
    Game updateGame(long id, Game newGame);
    void deleteGame(long id);

    Long getMaxGameId();
    List<Game> getGamesByUserId(Long userId);
}

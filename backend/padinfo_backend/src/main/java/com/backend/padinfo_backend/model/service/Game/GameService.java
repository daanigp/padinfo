package com.backend.padinfo_backend.model.service.Game;

import com.backend.padinfo_backend.exceptions.game.GameDeleteException;
import com.backend.padinfo_backend.exceptions.game.GameNotFoundException;
import com.backend.padinfo_backend.model.entity.Game;
import com.backend.padinfo_backend.model.repository.IGameRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Hidden
@Service
public class GameService implements IGameService{

    @Autowired
    private IGameRepository gameRepository;

    @Override
    public List<Game> findAll() {
        return (List<Game>) gameRepository.findAll();
    }

    @Override
    public Game findById(long id) {
        return gameRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException(id)
        );
    }

    @Override
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game updateGame(long id, Game newGame) {

        Game game = gameRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException("Error al buscar el partido -> " + id)
        );

        newGame.setId(game.getId());

        return gameRepository.save(newGame);
    }

    @Override
    public void deleteGame(long id) {
        try {
            gameRepository.deleteById(id);
        } catch (Exception ex) {
            throw new GameDeleteException(id, ex);
        }
    }
}

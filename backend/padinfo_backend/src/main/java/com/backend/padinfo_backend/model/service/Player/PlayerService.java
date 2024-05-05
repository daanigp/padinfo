package com.backend.padinfo_backend.model.service.Player;

import com.backend.padinfo_backend.exceptions.player.PlayerDeleteException;
import com.backend.padinfo_backend.exceptions.player.PlayerNotFoundException;
import com.backend.padinfo_backend.model.entity.Player;
import com.backend.padinfo_backend.model.repository.IPlayerRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Hidden
@Service
public class PlayerService implements IPlayerService {

    @Autowired
    private IPlayerRepository playerRepository;

    @Override
    public List<Player> findAll() {
        return (List<Player>) playerRepository.findAll();
    }

    @Override
    public Player findById(long id) {
        return playerRepository.findById(id).orElseThrow(
                () -> new PlayerNotFoundException(id)
        );
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(long id, Player newPlayer) {

        Player player = playerRepository.findById(id).orElseThrow(
                () -> new PlayerNotFoundException("Error al buscar el jugador -> " + id)
        );

        newPlayer.setId(player.getId());

        return playerRepository.save(newPlayer);
    }

    @Override
    public void deletePlayer(long id) {
        try {
            playerRepository.deleteById(id);
        } catch (Exception ex) {
            throw new PlayerDeleteException(id, ex);
        }
    }
}

package com.backend.padinfo_backend.model.service.Player;

import com.backend.padinfo_backend.model.entity.Player;

import java.util.List;

public interface IPlayerService {
    List<Player> findAll();
    Player findById(long id);
    Player createPlayer(Player player);
    Player updatePlayer(long id, Player newPlayer);
    void deletePlayer(long id);
}

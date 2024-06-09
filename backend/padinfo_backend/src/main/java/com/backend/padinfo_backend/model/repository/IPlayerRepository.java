package com.backend.padinfo_backend.model.repository;

import com.backend.padinfo_backend.model.entity.Player;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Hidden
@Repository
public interface IPlayerRepository extends CrudRepository<Player, Long> {
    List<Player> getPlayersByGender(String gender);
}

package com.backend.padinfo_backend.model.repository;

import com.backend.padinfo_backend.model.entity.Game;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Hidden
@Repository
public interface IGameRepository extends CrudRepository<Game, Long> {
    // 8
    Long getMaxGameId();
}

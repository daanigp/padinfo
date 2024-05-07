package com.backend.padinfo_backend.mappers;

import com.backend.padinfo_backend.dto.game.GameDTO;
import com.backend.padinfo_backend.model.entity.Game;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GameMapper {
    @Autowired
    private ModelMapper modelMapper;

    public GameDTO toDTO(Game game) {
        return modelMapper.map(game, GameDTO.class);
    }

    public List<GameDTO> toDTO(List<Game> games) {
        return games.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

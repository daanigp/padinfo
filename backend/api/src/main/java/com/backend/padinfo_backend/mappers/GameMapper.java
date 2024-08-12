package com.backend.padinfo_backend.mappers;

import com.backend.padinfo_backend.dto.game.CreateGameDTO;
import com.backend.padinfo_backend.dto.game.GameDTO;
import com.backend.padinfo_backend.dto.game.UpdateGameDTO;
import com.backend.padinfo_backend.model.entity.Game;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.service.UserInfo.IUserInfoService;
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

    @Autowired
    private IUserInfoService userInfoService;

    public GameDTO toDTO(Game game) {
        return modelMapper.map(game, GameDTO.class);
    }

    public Game fromDTO(CreateGameDTO gameDTO) {

        UserInfo user = userInfoService.findById(gameDTO.getUser_id());

        modelMapper.typeMap(CreateGameDTO.class, Game.class).addMappings(mapper -> {
            mapper.map(src -> user, Game::setUserInfo);
        });

        return modelMapper.map(gameDTO, Game.class);
    }

    public Game fromDTO(UpdateGameDTO gameDTO) {
        return modelMapper.map(gameDTO, Game.class);
    }

    public List<GameDTO> toDTO(List<Game> games) {
        return games.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

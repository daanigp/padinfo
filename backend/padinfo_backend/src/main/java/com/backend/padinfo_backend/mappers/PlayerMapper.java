package com.backend.padinfo_backend.mappers;

import com.backend.padinfo_backend.dto.player.PlayerDTO;
import com.backend.padinfo_backend.model.entity.Player;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PlayerMapper {
    @Autowired
    private ModelMapper modelMapper;

    public PlayerDTO toDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    public List<PlayerDTO> toDTO(List<Player> players) {
        return players.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

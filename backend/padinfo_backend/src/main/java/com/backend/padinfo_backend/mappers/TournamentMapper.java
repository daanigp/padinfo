package com.backend.padinfo_backend.mappers;

import com.backend.padinfo_backend.dto.tournament.TournamentDTO;
import com.backend.padinfo_backend.model.entity.Tournament;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TournamentMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TournamentDTO toDTO(Tournament tournament) {
        return modelMapper.map(tournament, TournamentDTO.class);
    }

    public List<TournamentDTO> toDTO(List<Tournament> tournaments) {
        return tournaments.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

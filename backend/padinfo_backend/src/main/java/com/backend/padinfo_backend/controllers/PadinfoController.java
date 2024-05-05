package com.backend.padinfo_backend.controllers;

import com.backend.padinfo_backend.dto.player.PlayerDTO;
import com.backend.padinfo_backend.dto.tournament.TournamentDTO;
import com.backend.padinfo_backend.mappers.PlayerMapper;
import com.backend.padinfo_backend.mappers.TournamentMapper;
import com.backend.padinfo_backend.model.entity.Player;
import com.backend.padinfo_backend.model.entity.Tournament;
import com.backend.padinfo_backend.model.service.Player.IPlayerService;
import com.backend.padinfo_backend.model.service.Tournament.ITournamentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*"})
@Tag(name = "PADINFO", description = "App de DANIELITOOOOO")
public class PadinfoController {

    /*
        Services
     */
    @Autowired
    private ITournamentService tournamentService;

    @Autowired
    private IPlayerService playerService;

    /*
        MAPPERS
     */
    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @GetMapping("/tournaments")
    public ResponseEntity<List<TournamentDTO>> getTournaments(){
        List<Tournament> tournaments = tournamentService.findAll();

        List<TournamentDTO> tournamentDTOs = tournamentMapper.toDTO(tournaments);

        return new ResponseEntity<>(tournamentDTOs, HttpStatus.OK);
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTO>> getPlayers() {
        List<Player> players = playerService.findAll();

        List<PlayerDTO> playerDTOs = playerMapper.toDTO(players);

        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

}

package com.backend.padinfo_backend.controllers;

import com.backend.padinfo_backend.dto.game.GameDTO;
import com.backend.padinfo_backend.dto.player.PlayerDTO;
import com.backend.padinfo_backend.dto.tournament.TournamentDTO;
import com.backend.padinfo_backend.dto.userInfo.UserDTO;
import com.backend.padinfo_backend.exceptions.Response;
import com.backend.padinfo_backend.mappers.GameMapper;
import com.backend.padinfo_backend.mappers.PlayerMapper;
import com.backend.padinfo_backend.mappers.TournamentMapper;
import com.backend.padinfo_backend.mappers.UserInfoMapper;
import com.backend.padinfo_backend.model.entity.Game;
import com.backend.padinfo_backend.model.entity.Player;
import com.backend.padinfo_backend.model.entity.Tournament;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.service.Game.IGameService;
import com.backend.padinfo_backend.model.service.Player.IPlayerService;
import com.backend.padinfo_backend.model.service.Tournament.ITournamentService;
import com.backend.padinfo_backend.model.service.UserInfo.IUserInfoService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.User;
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

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IGameService gameService;

    /*
        MAPPERS
     */
    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private GameMapper gameMapper;

    /*
        ENDPOINTS
     */

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

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserInfo> usersInfo = userInfoService.findAll();

        List<UserDTO> userDTOs = userInfoMapper.toDTO(usersInfo);

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameDTO>> getGames() {
        List<Game> games = gameService.findAll();

        List<GameDTO> gameDTOs = gameMapper.toDTO(games);

        return new ResponseEntity<>(gameDTOs, HttpStatus.OK);
    }

    // 13
    @GetMapping("/getUserInfoByUserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserInfo by id",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hay ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class)))

    })
    public ResponseEntity<UserDTO> getUserInfoByUserID(@RequestParam long id) {
        UserInfo userInfo = userInfoService.findById(id);

        UserDTO userDTO = userInfoMapper.toDTO(userInfo);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // 13
    @GetMapping("/getUserInfoByUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserInfo by user",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hay ningun usuario con ese usuario",
                    content = @Content(schema = @Schema(implementation = Response.class)))

    })
    public ResponseEntity<UserDTO> getUserInfo(@RequestParam String user) {
        UserInfo userInfo = userInfoService.selectUserInfoByUsername(user);

        UserDTO userDTO = userInfoMapper.toDTO(userInfo);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}

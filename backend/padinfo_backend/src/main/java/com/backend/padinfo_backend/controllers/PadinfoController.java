package com.backend.padinfo_backend.controllers;

import com.backend.padinfo_backend.dto.game.CreateGameDTO;
import com.backend.padinfo_backend.dto.game.GameDTO;
import com.backend.padinfo_backend.dto.game.UpdateGameDTO;
import com.backend.padinfo_backend.dto.player.PlayerDTO;
import com.backend.padinfo_backend.dto.tournament.TournamentDTO;
import com.backend.padinfo_backend.dto.userInfo.Authentication.CreateUserDTO;
import com.backend.padinfo_backend.dto.userInfo.UpdateUserInfoDTO;
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
import jakarta.validation.Valid;
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
    // 6
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

    // 2
    @PostMapping("/usuario/registerNewUser")
    public ResponseEntity<UserInfo> createUser(@Valid @RequestBody CreateUserDTO userDTO) {
        UserInfo userInfo = userInfoMapper.fromDTO(userDTO);

        UserInfo newUser = userInfoService.createUserInfo(userInfo);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    // 3
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable long id, @Valid @RequestBody UpdateUserInfoDTO newUserInfoDTO) {
        UserInfo userInfo = null;

        UserInfo newUserInfo = userInfoMapper.fromDTO(newUserInfoDTO);
        userInfo = userInfoService.updateUserInfo(id, newUserInfo);

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    // 4
    @PutMapping("/usuarios/updateIsConnected/{id}")
    public ResponseEntity<Response> updateIsConnected(@PathVariable long id) {
        userInfoService.updateIsConnected(id);

        return new ResponseEntity<>(Response.noErrorResponse("IsConnected actualizado correctamente"), HttpStatus.OK);
    }

    // 5
    @GetMapping("/usuario/isConnected")
    public ResponseEntity<UserDTO> getUserConnected() {
        UserInfo userInfo = userInfoService.selectUserIsConnected();

        UserDTO userDTO = userInfoMapper.toDTO(userInfo);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // 7
    @GetMapping("/players/{gender}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByGender(@PathVariable String gender) {
        List<Player> players = playerService.getPlayersByGender(gender);

        List<PlayerDTO> playerDTOs = playerMapper.toDTO(players);

        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

    // 8
    @GetMapping("/games/getMaxIdGame")
    public ResponseEntity<Long> getMaximmumIdGame() {
        Long idGame = gameService.getMaxGameId();

        return new ResponseEntity<>(idGame, HttpStatus.OK);
    }

    // 9
    @PostMapping("/games/createNewGame")
    public ResponseEntity<Game> createGame(@Valid @RequestBody CreateGameDTO gameDTO) {
        Game game = gameMapper.fromDTO(gameDTO);

        Game newGame = gameService.createGame(game);

        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }

    // 10
    @PutMapping("/games/updateGame/{id}")
    public ResponseEntity<?> updateGame(@PathVariable long id, @Valid @RequestBody UpdateGameDTO newGameDTO) {
        Game game = null;

        Game newGame = gameMapper.fromDTO(newGameDTO);
        game = gameService.updateGame(id, newGame);

        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    // 11
    @GetMapping("/games/user/{id}")
    public ResponseEntity<List<GameDTO>> getGamesByUserId(@PathVariable long id) {

        List<Game> games = gameService.getGamesByUserId(id);

        List<GameDTO> gamesDTO = gameMapper.toDTO(games);

        return new ResponseEntity<>(gamesDTO, HttpStatus.OK);
    }

    // 12
    @DeleteMapping("/games/deleteGame/{id}")
    public ResponseEntity<Response> deleteGameById(@PathVariable long id) {
        gameService.deleteGame(id);

        return new ResponseEntity<>(Response.noErrorResponse("Partido eliminado correctamente."), HttpStatus.OK);
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


    // 14
    @DeleteMapping("/users/deleteUser/{id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable long id) {
        userInfoService.deleteUserInfo(id);

        return new ResponseEntity<>(Response.noErrorResponse("Usuario eliminado correctamente"), HttpStatus.OK);
    }

}

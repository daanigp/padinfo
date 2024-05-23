package com.backend.padinfo_backend.controllers;

import com.backend.padinfo_backend.dto.game.CreateGameDTO;
import com.backend.padinfo_backend.dto.game.GameDTO;
import com.backend.padinfo_backend.dto.game.UpdateGameDTO;
import com.backend.padinfo_backend.dto.player.CreateUpdatePlayerDTO;
import com.backend.padinfo_backend.dto.player.PlayerDTO;
import com.backend.padinfo_backend.dto.tournament.CreateUpdateTournamentDTO;
import com.backend.padinfo_backend.dto.tournament.TournamentDTO;
import com.backend.padinfo_backend.dto.userInfo.Authentication.CreateUserDTO;
import com.backend.padinfo_backend.dto.userInfo.UpdateUserInfoDTO;
import com.backend.padinfo_backend.dto.userInfo.UserDTO;
import com.backend.padinfo_backend.exceptions.Response;
import com.backend.padinfo_backend.mappers.GameMapper;
import com.backend.padinfo_backend.mappers.PlayerMapper;
import com.backend.padinfo_backend.mappers.TournamentMapper;
import com.backend.padinfo_backend.mappers.UserInfoMapper;
import com.backend.padinfo_backend.model.entity.*;
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
    @GetMapping("/users/info/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserInfo by id",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hay ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class)))

    })
    public ResponseEntity<UserDTO> getUserInfoByUserID(@PathVariable long id) {
        UserInfo userInfo = userInfoService.findById(id);

        UserDTO userDTO = userInfoMapper.toDTO(userInfo);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // 2
    @PostMapping("/signup/newUser")
    public ResponseEntity<UserInfo> createUser(@Valid @RequestBody CreateUserDTO userDTO) {
        UserInfo userInfo = userInfoMapper.fromDTO(userDTO);

        UserInfo newUser = userInfoService.createUserInfo(userInfo);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    // 3
    @PutMapping("/users/updateInfo/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable long id, @Valid @RequestBody UpdateUserInfoDTO newUserInfoDTO) {
        UserInfo userInfo = null;

        UserInfo newUserInfo = userInfoMapper.fromDTO(newUserInfoDTO);
        userInfo = userInfoService.updateUserInfo(id, newUserInfo);

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    // 4
    @PutMapping("/users/updateIsConnected/{id}")
    public ResponseEntity<Response> updateIsConnected(@PathVariable long id) {
        userInfoService.updateIsConnected(id);

        return new ResponseEntity<>(Response.noErrorResponse("IsConnected actualizado correctamente"), HttpStatus.OK);
    }

    // 5
    @GetMapping("/users/isConnected")
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
    @GetMapping("/users/userInfoByName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserInfo by user",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hay ningun usuario con ese nombre usuario",
                    content = @Content(schema = @Schema(implementation = Response.class)))

    })
    public ResponseEntity<UserDTO> getUserInfo(@RequestParam String username) {
        UserInfo userInfo = userInfoService.selectUserInfoByUsername(username);

        UserDTO userDTO = userInfoMapper.toDTO(userInfo);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    // 14
    @DeleteMapping("/users/deleteUser/{id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable long id) {
        userInfoService.deleteUserInfo(id);

        return new ResponseEntity<>(Response.noErrorResponse("Usuario eliminado correctamente"), HttpStatus.OK);
    }

    // 15
    @PostMapping("/tournaments/createTournament")
    public ResponseEntity<Tournament> createTournament(@Valid @RequestBody CreateUpdateTournamentDTO tournamentDTO) {
        Tournament tournament = tournamentMapper.fromDTO(tournamentDTO);

        Tournament newTournament = tournamentService.createTournament(tournament);

        return new ResponseEntity<>(newTournament, HttpStatus.OK);
    }

    // 16
    @PutMapping("/tournaments/updateTournament/{id}")
    public ResponseEntity<?> updateTournament(@PathVariable long id, @Valid @RequestBody CreateUpdateTournamentDTO newTournamentDTO){
        Tournament tournament = null;

        Tournament newTournament = tournamentMapper.fromDTO(newTournamentDTO);
        tournament = tournamentService.updateTournament(id, newTournament);

        return new ResponseEntity<>(tournament, HttpStatus.OK);
    }

    // 17
    @DeleteMapping("/tournaments/deleteTournament/{id}")
    public ResponseEntity<Response> deleteTournamentById(@PathVariable long id) {
        tournamentService.deleteTournament(id);

        return new ResponseEntity<>(Response.noErrorResponse("Torneo eliminado correctamente."), HttpStatus.OK);
    }

    // 18
    @PostMapping("/players/createPlayer")
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody CreateUpdatePlayerDTO playerDTO) {
        Player player = playerMapper.fromDTO(playerDTO);

        Player newPlayer = playerService.createPlayer(player);

        return new ResponseEntity<>(newPlayer, HttpStatus.OK);
    }

    // 19
    @PutMapping("/players/updatePlayer/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable long id, @Valid @RequestBody CreateUpdatePlayerDTO newPlayerDTO) {
        Player player = null;

        Player newPlayer = playerMapper.fromDTO(newPlayerDTO);
        player = playerService.updatePlayer(id, newPlayer);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    // 20
    @DeleteMapping("/players/deletePlayer/{id}")
    public ResponseEntity<Response> deletePlayerById(@PathVariable long id) {
        playerService.deletePlayer(id);

        return new ResponseEntity<>(Response.noErrorResponse("Jugador eliminado correctamente."), HttpStatus.OK);
    }

    @GetMapping("/users/checkUser")
    public ResponseEntity<Response> checkUserExists(@RequestParam String username) {
        String message;

        if (userInfoService.existsByUsername(username)) {
            message = "Existe";
        } else {
            message = "No existe";
        }

        return new ResponseEntity<>(Response.noErrorResponse(message), HttpStatus.OK);
    }

    @GetMapping("/users/getRoles/{id}")
    public ResponseEntity<?> getUserRolesByUserId(@PathVariable long id) {
        List<Role> roles = userInfoService.getRolesByUserId(id);

        if (roles.isEmpty()) {
            String message = "No hay roles asociados al id '" + id + "'";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }
    }

}

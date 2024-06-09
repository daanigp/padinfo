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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Tag(name = "PADINFO")
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

    /**
     * Tournaments
     */
    @Operation(summary = "Método que devuelve un listado con todos los torneos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve todos los torneos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TournamentDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/tournaments")
    public ResponseEntity<List<TournamentDTO>> getTournaments(){
        List<Tournament> tournaments = tournamentService.findAll();

        List<TournamentDTO> tournamentDTOs = tournamentMapper.toDTO(tournaments);

        return new ResponseEntity<>(tournamentDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Método que crea un nuevo torneo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un torneo",
                    content = @Content(schema = @Schema(implementation = Tournament.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @PostMapping("/tournaments/createTournament")
    public ResponseEntity<Tournament> createTournament(@Valid @RequestBody CreateUpdateTournamentDTO tournamentDTO) {
        Tournament tournament = tournamentMapper.fromDTO(tournamentDTO);

        Tournament newTournament = tournamentService.createTournament(tournament);

        return new ResponseEntity<>(newTournament, HttpStatus.OK);
    }

    @Operation(summary = "Método que actualiza un torneo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualiza un torneo",
                    content = @Content(schema = @Schema(implementation = Tournament.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun torneo con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @PutMapping("/tournaments/updateTournament/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable long id, @Valid @RequestBody CreateUpdateTournamentDTO newTournamentDTO){
        Tournament tournament = null;

        Tournament newTournament = tournamentMapper.fromDTO(newTournamentDTO);
        tournament = tournamentService.updateTournament(id, newTournament);

        return new ResponseEntity<>(tournament, HttpStatus.OK);
    }

    @Operation(summary = "Método que elimina un torneo por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimina un torneo",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun torneo con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @DeleteMapping("/tournaments/deleteTournament/{id}")
    public ResponseEntity<Response> deleteTournamentById(@PathVariable long id) {
        tournamentService.deleteTournament(id);

        return new ResponseEntity<>(Response.noErrorResponse("Borrado"), HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve un torneo por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve el torneo",
                    content = @Content(schema = @Schema(implementation = TournamentDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hay ningun torneo con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/tournaments/info/{id}")
    public ResponseEntity<TournamentDTO> getTournamentByID(@PathVariable long id) {
        Tournament tournament = tournamentService.findById(id);

        TournamentDTO tournamentDTO = tournamentMapper.toDTO(tournament);

        return new ResponseEntity<>(tournamentDTO, HttpStatus.OK);
    }





    /**
     * Players
     */
    @Operation(summary = "Método que devuelve un listado con todos los jugadores y jugadoras existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve todos los jugadores y jugadoras",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlayerDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTO>> getPlayers() {
        List<Player> players = playerService.findAll();

        List<PlayerDTO> playerDTOs = playerMapper.toDTO(players);

        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve un listado con todos los jugadores existentes según su género")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve todos los jugadores según su género",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlayerDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/players/selectType/{gender}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByGender(@PathVariable String gender) {
        List<Player> players = playerService.getPlayersByGender(gender);

        List<PlayerDTO> playerDTOs = playerMapper.toDTO(players);

        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Método que crea un jugador/a")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un jugador/a",
                    content = @Content(schema = @Schema(implementation = Player.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @PostMapping("/players/createPlayer")
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody CreateUpdatePlayerDTO playerDTO) {
        Player player = playerMapper.fromDTO(playerDTO);

        Player newPlayer = playerService.createPlayer(player);

        return new ResponseEntity<>(newPlayer, HttpStatus.OK);
    }

    @Operation(summary = "Método que actualiza un jugador/a")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualiza un jugador/a",
                    content = @Content(schema = @Schema(implementation = Player.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun jugador con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @PutMapping("/players/updatePlayer/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable long id, @Valid @RequestBody CreateUpdatePlayerDTO newPlayerDTO) {
        Player player = null;

        Player newPlayer = playerMapper.fromDTO(newPlayerDTO);
        player = playerService.updatePlayer(id, newPlayer);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @Operation(summary = "Método que elimina un jugador/a por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimina un jugador/a",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun jugador con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @DeleteMapping("/players/deletePlayer/{id}")
    public ResponseEntity<Response> deletePlayerById(@PathVariable long id) {
        playerService.deletePlayer(id);

        return new ResponseEntity<>(Response.noErrorResponse("Borrado"), HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve un jugador/a por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve un jugador/a",
                    content = @Content(schema = @Schema(implementation = PlayerDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hay ningun player con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")

    })
    @GetMapping("/players/info/{id}")
    public ResponseEntity<PlayerDTO> getPlayerByID(@PathVariable long id) {
        Player player = playerService.findById(id);

        PlayerDTO playerDTO = playerMapper.toDTO(player);

        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }





    /**
     * Users
     */
    @Operation(summary = "Método que devuelve un listado con todos los usuarios existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve todos los usuarios",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserInfo> usersInfo = userInfoService.findAll();

        List<UserDTO> userDTOs = userInfoMapper.toDTO(usersInfo);

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve la información de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve la información del usuario",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/users/info/{id}")
    public ResponseEntity<UserDTO> getUserInfoByUserID(@PathVariable long id) {
        UserInfo userInfo = userInfoService.findById(id);

        UserDTO userDTO = userInfoMapper.toDTO(userInfo);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "Método que actualiza la información de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualiza la información del usuario",
                    content = @Content(schema = @Schema(implementation = UserInfo.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @PutMapping("/users/updateInfo/{id}")
    public ResponseEntity<UserInfo> updateUserInfo(@PathVariable long id, @Valid @RequestBody UpdateUserInfoDTO newUserInfoDTO) {
        UserInfo userInfo = null;

        UserInfo newUserInfo = userInfoMapper.fromDTO(newUserInfoDTO);
        userInfo = userInfoService.updateUserInfo(id, newUserInfo);

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @Operation(summary = "Método que actualiza el atributo isConnected de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualiza el atributo isConnected del usuario",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/users/updateIsConnected/{id}")
    public ResponseEntity<Response> updateIsConnected(@PathVariable long id) {
        userInfoService.updateIsConnected(id);

        return new ResponseEntity<>(Response.noErrorResponse("IsConnected actualizado correctamente"), HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve el valor del atributo isConnected de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve el valor del atributo isConnected del usuario",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/users/isConnected/{id}")
    public ResponseEntity<Integer> getUserConnectivityByUserId(@PathVariable long id) {
        int isConnected = userInfoService.selectUserIsConnectedByUserId(id);

        return new ResponseEntity<>(isConnected, HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve la info de un usuario según su nombre de usuario (username)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve la info del usuario",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "No hay ningun usuario con ese nombre usuario",
                    content = @Content(schema = @Schema(implementation = Response.class)))

    })
    @GetMapping("/users/userInfoByName")
    public ResponseEntity<UserDTO> getUserInfo(@RequestParam String username) {
        UserInfo userInfo = userInfoService.selectUserInfoByUsername(username);

        UserDTO userDTO = userInfoMapper.toDTO(userInfo);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "Método que elimina un usuario por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimina un usuario",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @DeleteMapping("/users/deleteUser/{id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable long id) {
        userInfoService.deleteUserInfo(id);

        return new ResponseEntity<>(Response.noErrorResponse("Usuario eliminado correctamente"), HttpStatus.OK);
    }

    @Operation(summary = "Método que comprueba si un usuario existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funciona correctamente",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
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

    @Operation(summary = "Método que devuelve los roles de un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve los roles",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
            @ApiResponse(responseCode = "404", description = "No existe ningun usuario con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/users/getRoles/{id}")
    public ResponseEntity<List<Long>> getUserRolesByUserId(@PathVariable long id) {
        List<Long> roles = userInfoService.getRolesByUserId(id);

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }





    /**
     * Games
     */
    @Operation(summary = "Método que devuelve un listado con todos los partidos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve todos los partidos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GameDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/games")
    public ResponseEntity<List<GameDTO>> getGames() {
        List<Game> games = gameService.findAll();

        List<GameDTO> gameDTOs = gameMapper.toDTO(games);

        return new ResponseEntity<>(gameDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve el id más alto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve el id más alto",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/games/getMaxIdGame")
    public ResponseEntity<Long> getMaximmumIdGame() {
        Long idGame = gameService.getMaxGameId();

        return new ResponseEntity<>(idGame, HttpStatus.OK);
    }

    @Operation(summary = "Método que crea un nuevo partido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crea un nuevo partido",
                    content = @Content(schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @PostMapping("/games/createNewGame")
    public ResponseEntity<Game> createGame(@Valid @RequestBody CreateGameDTO gameDTO) {
        Game game = gameMapper.fromDTO(gameDTO);

        Game newGame = gameService.createGame(game);

        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }

    @Operation(summary = "Método que actualiza un partido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualiza un partido",
                    content = @Content(schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @PutMapping("/games/updateGame/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable long id, @Valid @RequestBody UpdateGameDTO newGameDTO) {
        Game game = null;

        Game newGame = gameMapper.fromDTO(newGameDTO);
        game = gameService.updateGame(id, newGame);

        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve un listado con todos los partidos de un usuario existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve todos los partidos de un usuario",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GameDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/games/user/{userId}")
    public ResponseEntity<List<GameDTO>> getGamesByUserId(@PathVariable long userId) {

        List<Game> games = gameService.getGamesByUserId(userId);

        List<GameDTO> gamesDTO = gameMapper.toDTO(games);

        return new ResponseEntity<>(gamesDTO, HttpStatus.OK);
    }

    @Operation(summary = "Método que elimina un partido de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimina un partido de un usuario",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun partido con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @DeleteMapping("/games/deleteGame/{id}")
    public ResponseEntity<Response> deleteGameById(@PathVariable long id) {
        gameService.deleteGame(id);

        return new ResponseEntity<>(Response.noErrorResponse("Borrado"), HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve un partido de un usuario según el id del partido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve el partido",
                    content = @Content(schema = @Schema(implementation = GameDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningun partido con ese id",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "Error de autorización")
    })
    @GetMapping("/games/user/getGame/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable long id) {
        Game game = gameService.findById(id);

        GameDTO gameDTO = gameMapper.toDTO(game);

        return new ResponseEntity<>(gameDTO, HttpStatus.OK);
    }
}

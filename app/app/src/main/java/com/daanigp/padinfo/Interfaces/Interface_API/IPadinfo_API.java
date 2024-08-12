package com.daanigp.padinfo.Interfaces.Interface_API;

import com.daanigp.padinfo.Entity.CreateGame;
import com.daanigp.padinfo.Entity.CreateUpdatePlayer;
import com.daanigp.padinfo.Entity.CreateUpdateTournament;
import com.daanigp.padinfo.Entity.Game;
import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.UpdateGame;
import com.daanigp.padinfo.Entity.UpdateUserInfo;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Entity.Player;
import com.daanigp.padinfo.Entity.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPadinfo_API {

    /*
        NO NEED TOKEN
     */
    @GET("users/checkUser")
    Call<ResponseEntity> checkUserExistsByUsername(@Query("username") String username);

    @PUT("users/updateIsConnected/{id}")
    Call<ResponseEntity> updateIsConnected(@Path("id") long id);

    @GET("users/userInfoByName")
    Call<UserEntity> getUserByUsername(@Query("username") String username);

    @GET("users/isConnected/{id}")
    Call<Integer> getUserConnectivityByUserId(@Path("id") long id);

    /*
        NEED TOKEN
     */
    @GET("tournaments")
    Call<List<Tournament>> getTournaments(@Header("Authorization") String token);

    @GET("players")
    Call<List<Player>> getPlayers(@Header("Authorization") String token);

    @GET("players/selectType/{gender}")
    Call<List<Player>> getPlayersByGender(
        @Header("Authorization") String token,
        @Path("gender") String gender
    );

    @GET("users/info/{id}")
    Call<UserEntity> getUserInfoByUserID(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @GET("users/getRoles/{id}")
    Call<List<Long>> getRolesByUserId(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @PUT("users/updateInfo/{id}")
    Call<UserEntity> updateUserInfo(
        @Header("Authorization") String token,
        @Path("id") long id,
        @Body UpdateUserInfo updateUserInfo
    );

    @GET("games/user/{userId}")
    Call<List<Game>> getGamesByUserId(
        @Header("Authorization") String token,
        @Path("userId") long userId
    );

    @DELETE("games/deleteGame/{id}")
    Call<ResponseEntity> deleteGameByid(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @GET("games/user/getGame/{id}")
    Call<Game> getGameByIdGame(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @GET("games/getMaxIdGame")
    Call<Long> getMaximmumIdGame(@Header("Authorization") String token);

    @POST("games/createNewGame")
    Call<Game> createGame(
        @Header("Authorization") String token,
        @Body CreateGame createGame
    );

    @PUT("games/updateGame/{id}")
    Call<Game> updateGame(
        @Header("Authorization") String token,
        @Path("id") long id,
        @Body UpdateGame updateGame
    );

    @GET("tournaments/info/{id}")
    Call<Tournament> findTournamentById(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @POST("tournaments/createTournament")
    Call<Tournament> createTournament(
        @Header("Authorization") String token,
        @Body CreateUpdateTournament createUpdateTournament
    );

    @PUT("tournaments/updateTournament/{id}")
    Call<Tournament> updateTournament(
        @Header("Authorization") String token,
        @Path("id") long id,
        @Body CreateUpdateTournament createUpdateTournament
    );

    @DELETE("tournaments/deleteTournament/{id}")
    Call<ResponseEntity> deleteTournamentById(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @GET("players/info/{id}")
    Call<Player> findPlayerById(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @POST("players/createPlayer")
    Call<Player> createPlayer(
        @Header("Authorization") String token,
        @Body CreateUpdatePlayer createUpdatePlayer
    );

    @PUT("players/updatePlayer/{id}")
    Call<Player> updatePlayer(
        @Header("Authorization") String token,
        @Path("id") long id,
        @Body CreateUpdatePlayer createUpdatePlayer
    );

    @DELETE("players/deletePlayer/{id}")
    Call<ResponseEntity> deletePlayerById(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @DELETE("users/deleteUser/{id}")
    Call<ResponseEntity> deleteUserById(
            @Header("Authorization") String token,
            @Path("id") long id
    );






}

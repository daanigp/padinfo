package com.daanigp.padinfo.Interface_API;

import com.daanigp.padinfo.Entity.Respone.ResponseEntity;
import com.daanigp.padinfo.Entity.UserEntity;
import com.daanigp.padinfo.Player;
import com.daanigp.padinfo.Torneo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    /*
        NEED TOKEN
     */
    @GET("tournaments")
    Call<List<Torneo>> getTournaments(@Header("Authorization") String token);

    @GET("players")
    Call<List<Player>> getPlayers(@Header("Authorization") String token);

    @GET("/players/{gender}")
    Call<List<Player>> getPlayersByGender(
            @Header("Authorization") String token,
            @Path("gender") String gender
    );


}

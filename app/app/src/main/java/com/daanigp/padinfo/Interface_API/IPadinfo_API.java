package com.daanigp.padinfo.Interface_API;

import com.daanigp.padinfo.Entity.Respone.Response;
import com.daanigp.padinfo.Torneo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IPadinfo_API {

    @GET("users/checkUser")
    Call<Response> checkUserExistsByUsername(@Query("username") String username);

    @GET("tournaments")
    Call<List<Torneo>> getTournaments(@Header("Authorization") String token);


}

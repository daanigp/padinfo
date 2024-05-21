package com.daanigp.padinfo.Interface_API;

import com.daanigp.padinfo.Torneo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IPadinfo_API {
    @GET("tournaments")
    Call<List<Torneo>> getTournaments(@Header("Authorization") String token);
}

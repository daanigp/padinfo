package com.daanigp.padinfo.Interface_API;

import com.daanigp.padinfo.Security.Entity.Login;
import com.daanigp.padinfo.Security.Entity.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISecurityPadinfo_API {

    @POST("login")
    Call<String> loginUser(@Body Login loginUser);

}

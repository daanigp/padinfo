package com.daanigp.padinfo.Interface_API;

import com.daanigp.padinfo.Entity.Respone.Response;
import com.daanigp.padinfo.Entity.Security.CreateUser;
import com.daanigp.padinfo.Entity.Security.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISecurityPadinfo_API {

    @POST("login")
    Call<String> loginUser(@Body LoginUser loginUser);

    @POST("signup")
    Call<Response> registerUser(@Body CreateUser createUser);
}

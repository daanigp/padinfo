package com.daanigp.padinfo.Retrofit;

import com.daanigp.padinfo.Interfaces.Interface_API.ISecurityPadinfo_API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSecurityClient {

    //private static final String BASE_URL = "http://54.197.161.208/auth/"; -> AWS
    private static final String BASE_URL = "https://55fa-2-141-39-112.ngrok-free.app/auth/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static ISecurityPadinfo_API getSecurityPadinfoAPI() {
        return getClient().create(ISecurityPadinfo_API.class);
    }
}

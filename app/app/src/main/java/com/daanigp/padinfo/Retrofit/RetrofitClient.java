package com.daanigp.padinfo.Retrofit;

import com.daanigp.padinfo.Interfaces.Interface_API.IPadinfo_API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://0552-2-141-35-122.ngrok-free.app/api/";
    //private static final String BASE_URL = "http://54.197.161.208/api/"; -> AWS
    //private static final String BASE_URL = "https://55fa-2-141-39-112.ngrok-free.app/api/";
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

    public static IPadinfo_API getPadinfoAPI() {
        return getClient().create(IPadinfo_API.class);
    }
}

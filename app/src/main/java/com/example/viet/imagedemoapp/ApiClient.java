package com.example.viet.imagedemoapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by viet on 07/08/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://192.168.0.150/ImageApi/";
    private static Retrofit mRetrofit = null;

    public static Retrofit getApiClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }
}

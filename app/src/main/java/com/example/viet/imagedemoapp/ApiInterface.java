package com.example.viet.imagedemoapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by viet on 07/08/2017.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("upload.php")
    Call<Image> uploadImage(@Field("image") String image, @Field("title") String title);

    @GET("select.php")
    Call<ArrayList<Image>> getImage(@Query("num") String num);

    @GET("delete.php")
    Call<String> deleteImage(@Query("id") String id, @Query("path") String path);
}

package com.example.viet.imagedemoapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by viet on 07/08/2017.
 */

public class Image {
    @SerializedName("title")
    private String title;
    @SerializedName("path")
    private String image;

    public Image(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

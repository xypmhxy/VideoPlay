package com.video.ren.videoplay.beans;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/9/5
 */

public class Video {
    private   int id;
    private String name;
    private String data;
    private String durtion;
    private Bitmap Thumbnail;

    public Video(int id, String name, String data, String durtion, Bitmap thumbnail) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.durtion = durtion;
        Thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDurtion() {
        return durtion;
    }

    public void setDurtion(String durtion) {
        this.durtion = durtion;
    }

    public Bitmap getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        Thumbnail = thumbnail;
    }
}

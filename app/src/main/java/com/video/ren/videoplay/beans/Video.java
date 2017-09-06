package com.video.ren.videoplay.beans;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/9/5
 */

public class Video implements Parcelable{


    private   int id;
    private String name;
    private String data;
    private long size;
    private String durtion;
    private Bitmap Thumbnail;


    protected Video(Parcel in) {
        id = in.readInt();
        name = in.readString();
        data = in.readString();
        size = in.readLong();
        durtion = in.readString();
        Thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(data);
        dest.writeLong(size);
        dest.writeString(durtion);
        dest.writeParcelable(Thumbnail, flags);
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

    public Video(int id, String name, String data, long size, String durtion, Bitmap thumbnail) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.size = size;
        this.durtion = durtion;
        Thumbnail = thumbnail;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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

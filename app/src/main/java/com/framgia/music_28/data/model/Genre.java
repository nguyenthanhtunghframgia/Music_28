package com.framgia.music_28.data.model;

public class Genre {
    private String mName;
    private int mImageId;

    public Genre() {
    }

    public Genre(String name, int imageId) {
        mName = name;
        mImageId = imageId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }
}

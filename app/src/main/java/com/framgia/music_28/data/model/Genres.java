package com.framgia.music_28.data.model;

public class Genres {
    private String mName;
    private int mImageId;

    public Genres() {
    }

    public Genres(String name, int imageId) {
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

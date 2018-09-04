package com.framgia.music_28.data.model;

public class TrackBuilder {
    private int mId;
    private int mDuration;
    private String mTitle;
    private String mArtWorkUrl;
    private String mDownloadUrl;
    private boolean mIsDownloadable;
    private String mUri;
    private String mGenre;

    public TrackBuilder setId(int id) {
        mId = id;
        return this;
    }

    public TrackBuilder setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    public TrackBuilder setTitle(String title) {
        mTitle = title;
        return this;
    }

    public TrackBuilder setArtWorkUrl(String artWorkUrl) {
        mArtWorkUrl = artWorkUrl;
        return this;
    }

    public TrackBuilder setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
        return this;
    }

    public TrackBuilder setDownloadable(boolean downloadable) {
        mIsDownloadable = downloadable;
        return this;
    }

    public TrackBuilder setUri(String uri) {
        mUri = uri;
        return this;
    }

    public TrackBuilder setGenre(String genre) {
        mGenre = genre;
        return this;
    }

    public Track getTrack() {
        return new Track(mId, mDuration, mTitle, mArtWorkUrl,
                mDownloadUrl, mIsDownloadable, mUri, mGenre);
    }
}


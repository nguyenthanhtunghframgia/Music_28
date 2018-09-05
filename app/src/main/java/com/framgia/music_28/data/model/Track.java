package com.framgia.music_28.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
    private int mId;
    private int mDuration;
    private String mTitle;
    private String mArtWorkUrl;
    private String mDownloadUrl;
    private boolean mIsDownloadable;
    private String mUri;
    private String mGenre;

    public Track() {
    }

    public Track(int id, int duration, String title, String artWorkUrl, String downloadUrl,
                 boolean isDownloadable, String uri, String genre) {
        mId = id;
        mDuration = duration;
        mTitle = title;
        mArtWorkUrl = artWorkUrl;
        mDownloadUrl = downloadUrl;
        mIsDownloadable = isDownloadable;
        mUri = uri;
        mGenre = genre;
    }

    private Track(Parcel in) {
        mId = in.readInt();
        mDuration = in.readInt();
        mTitle = in.readString();
        mArtWorkUrl = in.readString();
        mDownloadUrl = in.readString();
        mIsDownloadable = in.readByte() != 0;
        mUri = in.readString();
        mGenre = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mDuration);
        dest.writeString(mTitle);
        dest.writeString(mArtWorkUrl);
        dest.writeString(mDownloadUrl);
        dest.writeByte((byte) (mIsDownloadable ? 1 : 0));
        dest.writeString(mUri);
        dest.writeString(mGenre);
    }

    public int getId() {
        return mId;
    }

    public int getDuration() {
        return mDuration;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getArtWorkUrl() {
        return mArtWorkUrl;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public boolean isDownloadable() {
        return mIsDownloadable;
    }

    public String getUri() {
        return mUri;
    }

    public String getGenre() {
        return mGenre;
    }

    public static class JsonKey {
        public static final String COLLECTION = "collection";
        public static final String TRACK = "track";
        public static final String ID = "id";
        public static final String DURATION = "duration";
        public static final String TITLE = "title";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String DOWNLOAD_URL = "download_url";
        public static final String DOWNLOADABLE = "downloadable";
        public static final String URI = "uri";
        public static final String GENRE = "genre";
    }
}

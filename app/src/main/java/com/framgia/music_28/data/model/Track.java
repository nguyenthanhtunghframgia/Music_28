package com.framgia.music_28.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
    private int mId;
    private int mDuration;
    private String mTitle;
    private String mArtWorkUrl;
    private String mDownloadUrl;
    private int mDownloadCount;
    private int mLikeCount;
    private boolean mIsDownloadable;
    private String mUri;
    private String mGenre;
    private String mArtist;

    public Track() {
    }

    public Track(int id, int duration, String title, String artWorkUrl, String downloadUrl,
                 int likeCount, boolean isDownloadable, String uri, String genre, String artist,
                 int downloadCount) {
        mId = id;
        mDuration = duration;
        mTitle = title;
        mArtWorkUrl = artWorkUrl;
        mDownloadUrl = downloadUrl;
        mLikeCount = likeCount;
        mIsDownloadable = isDownloadable;
        mUri = uri;
        mGenre = genre;
        mArtist = artist;
        mDownloadCount = downloadCount;
    }

    private Track(Parcel in) {
        mId = in.readInt();
        mDuration = in.readInt();
        mTitle = in.readString();
        mArtWorkUrl = in.readString();
        mDownloadUrl = in.readString();
        mLikeCount = in.readInt();
        mIsDownloadable = in.readByte() != 0;
        mUri = in.readString();
        mArtist = in.readString();
        mDownloadCount = in.readInt();
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
        dest.writeInt(mLikeCount);
        dest.writeByte((byte) (mIsDownloadable ? 1 : 0));
        dest.writeString(mUri);
        dest.writeString(mArtist);
        dest.writeInt(mDownloadCount);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtWorkUrl() {
        return mArtWorkUrl;
    }

    public void setArtWorkUrl(String artWorkUrl) {
        mArtWorkUrl = artWorkUrl;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(int likeCount) {
        mLikeCount = likeCount;
    }

    public boolean isDownloadable() {
        return mIsDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mIsDownloadable = downloadable;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public int getDownloadCount() {
        return mDownloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        mDownloadCount = downloadCount;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }
}

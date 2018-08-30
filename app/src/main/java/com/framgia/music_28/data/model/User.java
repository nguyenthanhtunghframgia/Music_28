package com.framgia.music_28.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int mId;
    private String mKind;
    private String mPermalink;
    private String mUsername;
    private String mLastModified;
    private String mUri;
    private String mPermalinkUrl;
    private String mAvatarUrl;

    public final static Creator<User> CREATOR = new Creator<User>() {

        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }
    };

    protected User(Parcel in) {
        mId = in.readInt();
        mKind = in.readString();
        mPermalink = in.readString();
        mUsername = in.readString();
        mLastModified = in.readString();
        mUri = in.readString();
        mPermalinkUrl = in.readString();
        mAvatarUrl = in.readString();
    }

    public User() {
    }

    public User(int id, String kind, String permalink, String username, String lastModified,
                String uri, String permalinkUrl, String avatarUrl) {
        mId = id;
        mKind = kind;
        mPermalink = permalink;
        mUsername = username;
        mLastModified = lastModified;
        mUri = uri;
        mPermalinkUrl = permalinkUrl;
        mAvatarUrl = avatarUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mKind);
        dest.writeString(mPermalink);
        dest.writeString(mUsername);
        dest.writeString(mLastModified);
        dest.writeString(mUri);
        dest.writeString(mPermalinkUrl);
        dest.writeString(mAvatarUrl);
    }

    public int describeContents() {
        return 0;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(String permalink) {
        mPermalink = permalink;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        mPermalinkUrl = permalinkUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }
}

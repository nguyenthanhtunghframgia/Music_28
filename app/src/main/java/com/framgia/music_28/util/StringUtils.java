package com.framgia.music_28.util;

import android.os.Environment;

import com.framgia.music_28.BuildConfig;

public class StringUtils {
    public static final String DURATION_FORMAT = "mm:ss";

    public static String getDownloadPath() {
        StringBuilder builder = new StringBuilder();
        builder = builder.append(Environment.getExternalStorageDirectory())
                .append("/")
                .append(Environment.DIRECTORY_MUSIC);
        return builder.toString();
    }

    public static String getTrackAPI(String genre) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.BASE_API);
        builder.append(Constants.PARA_MUSIC_GENRE);
        builder.append(genre);
        builder.append(Constants.CLIENT_ID);
        builder.append(BuildConfig.API_KEY);
        return builder.toString();
    }

    public static String getStreamTrackAPI(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.BASE_STREAM_API);
        builder.append(id);
        builder.append(Constants.CLIENT_STREAM_ID);
        builder.append(BuildConfig.API_KEY);
        return builder.toString();
    }

    public static String getSearchTrackAPI(String title) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.BASE_SEARCH_API);
        builder.append(Constants.SEARCH_CLIENT_ID);
        builder.append(BuildConfig.API_KEY);
        builder.append(Constants.SEARCH_QUERY);
        builder.append(title);
        return builder.toString();
    }
}

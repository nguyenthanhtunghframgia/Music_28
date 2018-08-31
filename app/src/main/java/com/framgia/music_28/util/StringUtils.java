package com.framgia.music_28.util;

import com.framgia.music_28.BuildConfig;

public class StringUtils {

    public static String getTrackAPI(String genre) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.BASE_API);
        builder.append(Constants.PARA_MUSIC_GENRE);
        builder.append(genre);
        builder.append(Constants.CLIENT_ID);
        builder.append(BuildConfig.API_KEY);
        return builder.toString();
    }
}

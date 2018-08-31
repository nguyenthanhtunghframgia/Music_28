package com.framgia.music_28.data.source.local;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({GenreType.ALL_MUSIC, GenreType.ALL_AUDIO, GenreType.ALTER_NATIVE_ROCK,
        GenreType.AMBIENT, GenreType.CLASSICAL, GenreType.COUNTRY})
@Retention(RetentionPolicy.SOURCE)
public @interface GenreType {
    String ALL_MUSIC = "ALL MUSIC";
    String ALL_AUDIO = "ALL AUDIO";
    String ALTER_NATIVE_ROCK = "ALTERNATIVE ROCK";
    String AMBIENT = "AMBIENT";
    String CLASSICAL = "CLASSICAL";
    String COUNTRY = "COUNTRY";
}

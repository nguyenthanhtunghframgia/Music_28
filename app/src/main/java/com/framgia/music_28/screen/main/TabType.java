package com.framgia.music_28.screen.main;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TabType.ALL, TabType.GENRE, TabType.DOWNLOAD})
@Retention(RetentionPolicy.SOURCE)
public @interface TabType {
    int ALL = 0;
    int GENRE = 1;
    int DOWNLOAD = 2;
}

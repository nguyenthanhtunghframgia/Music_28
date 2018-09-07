package com.framgia.music_28.service;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({RepeatType.NONE, RepeatType.ONE, RepeatType.ALL})
@Retention(RetentionPolicy.SOURCE)
public @interface RepeatType {
    int NONE = 0;
    int ONE = 1;
    int ALL = 2;
}

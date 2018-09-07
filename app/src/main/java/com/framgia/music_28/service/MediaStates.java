package com.framgia.music_28.service;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.framgia.music_28.service.MediaStates.IDLE;
import static com.framgia.music_28.service.MediaStates.PAUSED;
import static com.framgia.music_28.service.MediaStates.PLAYING;
import static com.framgia.music_28.service.MediaStates.PREPARING;

@IntDef({IDLE, PLAYING, PAUSED, PREPARING})
@Retention(RetentionPolicy.SOURCE)
public @interface MediaStates {
    int IDLE = 0;
    int PREPARING = 1;
    int PLAYING = 2;
    int PAUSED = 3;
}

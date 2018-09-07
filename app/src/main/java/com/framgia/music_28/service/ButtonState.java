package com.framgia.music_28.service;

import android.support.annotation.IntDef;

import static com.framgia.music_28.service.ButtonState.PAUSED;
import static com.framgia.music_28.service.ButtonState.PLAYING;

@IntDef({PLAYING, PAUSED})
public @interface ButtonState {
    int PAUSED = 0;
    int PLAYING = 1;
}

package com.framgia.music_28.service;

import android.support.annotation.IntDef;

import static com.framgia.music_28.service.ShuffleType.OFF;
import static com.framgia.music_28.service.ShuffleType.ON;

@IntDef({ON, OFF})
public @interface ShuffleType {
    int OFF = 0;
    int ON = 1;
}

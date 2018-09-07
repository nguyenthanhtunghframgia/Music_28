package com.framgia.music_28.service;

import com.framgia.music_28.data.model.Track;

public interface MediaPlayerListener {
    void onTrackChange(Track track);

    void onShuffleChanged(@ShuffleType int shuffleType);

    void onRepeatChanged(@RepeatType int repeatType);

    void onMediaStateChanged(@MediaStates int mediaState);

    void onError();
}

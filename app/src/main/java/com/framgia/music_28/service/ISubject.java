package com.framgia.music_28.service;

public interface ISubject {
    void registerMediaPlayerListener(MediaPlayerListener listener);

    void unregisterMediaPlayerListener(MediaPlayerListener listener);

    void removeMediaPlayerListener();
}

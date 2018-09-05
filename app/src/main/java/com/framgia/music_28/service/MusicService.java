package com.framgia.music_28.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.framgia.music_28.data.model.Track;

import java.util.ArrayList;

public class MusicService extends Service {
    public static final String EXTRA_TRACK_POSITION =
            "com.framgia.music_28.service.EXTRA_TRACK_POSITION";
    public static final String EXTRA_TRACKS =
            "com.framgia.music_28.service.EXTRA_TRACKS";
    private ArrayList<Track> mTracks;
    private int mPosition;
    public IBinder mIBinder = new MyServiceBinder();

    public class MyServiceBinder extends Binder {
        public MusicService getMusicService() {
            return MusicService.this;
        }
    }

    public static Intent getServiceIntent(Context context, ArrayList<Track> tracks, int position) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(EXTRA_TRACK_POSITION, position);
        intent.putParcelableArrayListExtra(EXTRA_TRACKS, tracks);
        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTracks = intent.getParcelableArrayListExtra(EXTRA_TRACKS);
        mPosition = intent.getIntExtra(EXTRA_TRACK_POSITION, 0);
        return START_NOT_STICKY;
    }

    public Track getTrack() {
        return mTracks.get(mPosition);
    }
}

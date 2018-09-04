package com.framgia.music_28.data.source.local;

import android.content.Context;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.source.TrackDataSource;

import java.util.ArrayList;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private static TrackLocalDataSource sInstance;
    private Context mContext;

    public TrackLocalDataSource(Context context) {
        mContext = context;
    }

    public static TrackLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public ArrayList<Track> getTracks() {
        // TODO: 9/4/2018 get local download track 
        return null;
    }
}

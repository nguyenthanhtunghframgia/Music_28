package com.framgia.music_28.data.source.remote;

import com.framgia.music_28.data.source.TrackDataSource;
import com.framgia.music_28.util.StringUtils;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {
    private static TrackRemoteDataSource sInstance;

    public TrackRemoteDataSource() {
    }

    public static TrackRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TrackRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getTrackRemote(String genre, OnFetchDataListener onFetchDataListener) {
        new GetTrackFromUrl(onFetchDataListener).execute(StringUtils.getTrackAPI(genre));
    }
}

package com.framgia.music_28.data.repository;

import android.annotation.SuppressLint;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.source.TrackDataSource;
import com.framgia.music_28.data.source.local.TrackLocalDataSource;
import com.framgia.music_28.data.source.remote.TrackRemoteDataSource;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class TrackRepository implements TrackDataSource.RemoteDataSource, TrackDataSource.LocalDataSource {
    private static TrackRepository sInstance;
    private TrackLocalDataSource mTrackLocalDataSource;
    private TrackRemoteDataSource mTrackRemoteDataSource;

    @SuppressLint("RestrictedApi")
    public TrackRepository(TrackLocalDataSource trackLocalDataSource,
                           TrackRemoteDataSource trackRemoteDataSource) {
        mTrackLocalDataSource = checkNotNull(trackLocalDataSource);
        mTrackRemoteDataSource = checkNotNull(trackRemoteDataSource);
    }

    @SuppressLint("RestrictedApi")
    public static TrackRepository getInstance(TrackLocalDataSource trackLocalDataSource,
                                              TrackRemoteDataSource trackRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new TrackRepository(checkNotNull(trackLocalDataSource),
                    checkNotNull(trackRemoteDataSource));
        }
        return sInstance;
    }

    @Override
    public void getTrackRemote(String genre, OnFetchDataListener onFetchDataListener) {
        mTrackRemoteDataSource.getTrackRemote(genre, onFetchDataListener);
    }

    @Override
    public void searchTrackRemote(String title, OnFetchDataListener onFetchDataListener) {
        mTrackRemoteDataSource.searchTrackRemote(title, onFetchDataListener);
    }

    @Override
    public ArrayList<Track> getTracks() {
        return mTrackLocalDataSource.getTracks();
    }
}

package com.framgia.music_28.data.source;

import com.framgia.music_28.data.model.Track;

import java.util.ArrayList;

public interface TrackDataSource {
    interface LocalDataSource {
        ArrayList<Track> getTracks();
    }

    interface RemoteDataSource {
        void getTrackRemote(String genre, OnFetchDataListener onFetchDataListener);

        void searchTrackRemote(String title, OnFetchDataListener onFetchDataListener);

        interface OnFetchDataListener {
            void onSuccess(ArrayList<Track> tracks);

            void onError(Exception e);
        }
    }
}

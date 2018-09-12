package com.framgia.music_28.screen.main.tracks;

import android.annotation.SuppressLint;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.repository.TrackRepository;
import com.framgia.music_28.data.source.TrackDataSource;
import com.framgia.music_28.util.Constants;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class TracksPresenter implements TracksContract.Presenter {
    private TrackRepository mTrackRepository;
    private TracksContract.View mView;

    @SuppressLint("RestrictedApi")
    public TracksPresenter(TrackRepository trackRepository, TracksContract.View view) {
        mTrackRepository = checkNotNull(trackRepository);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void getTracks() {
        mTrackRepository.getTrackRemote(Constants.ALL_MUSIC,
                new TrackDataSource.RemoteDataSource.OnFetchDataListener() {
                    @Override
                    public void onSuccess(ArrayList<Track> tracks) {
                        mView.displayTracks(tracks);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void searchTracks(String title) {
        mTrackRepository.searchTrackRemote(title,
                new TrackDataSource.RemoteDataSource.OnFetchDataListener() {
                    @Override
                    public void onSuccess(ArrayList<Track> tracks) {
                        mView.displayTracks(tracks);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public void start() {

    }
}

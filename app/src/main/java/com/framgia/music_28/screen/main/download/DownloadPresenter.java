package com.framgia.music_28.screen.main.download;

import android.annotation.SuppressLint;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.repository.TrackRepository;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class DownloadPresenter implements DownloadContract.Presenter {
    private TrackRepository mTrackRepository;
    private DownloadContract.View mView;

    @SuppressLint("RestrictedApi")
    public DownloadPresenter(TrackRepository trackRepository, DownloadContract.View view) {
        mTrackRepository = checkNotNull(trackRepository);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void getTracks() {
        ArrayList<Track> tracks = mTrackRepository.getTracks();
        mView.displayTracks(tracks);
    }

    @Override
    public void start() {

    }
}

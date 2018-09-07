package com.framgia.music_28.screen.genredetail;

import android.annotation.SuppressLint;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.repository.TrackRepository;
import com.framgia.music_28.data.source.TrackDataSource;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class GenreDetailPresenter implements GenreDetailContract.Presenter {
    private TrackRepository mTrackRepository;
    private GenreDetailContract.View mView;

    @SuppressLint("RestrictedApi")
    public GenreDetailPresenter(TrackRepository trackRepository, GenreDetailContract.View view) {
        mTrackRepository = checkNotNull(trackRepository);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void getGenres(String genre) {
        mTrackRepository.getTrackRemote(genre, new TrackDataSource.RemoteDataSource.OnFetchDataListener() {
            @Override
            public void onSuccess(ArrayList<Track> tracks) {
                mView.displayGenres(tracks);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start() {

    }
}

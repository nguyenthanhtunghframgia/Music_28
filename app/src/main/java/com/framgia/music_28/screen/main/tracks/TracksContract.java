package com.framgia.music_28.screen.main.tracks;

import com.framgia.music_28.BasePresenter;
import com.framgia.music_28.BaseView;
import com.framgia.music_28.data.model.Track;

import java.util.ArrayList;

public interface TracksContract {
    interface View extends BaseView<Presenter> {
        void displayTracks(ArrayList<Track> tracks);
    }

    interface Presenter extends BasePresenter {
        void getTracks();
    }
}


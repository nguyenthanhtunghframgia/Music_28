package com.framgia.music_28.screen.genredetail;

import com.framgia.music_28.BasePresenter;
import com.framgia.music_28.BaseView;
import com.framgia.music_28.data.model.Track;

import java.util.ArrayList;

public interface GenreDetailContract {

    interface View extends BaseView<Presenter> {
        void displayGenres(ArrayList<Track> tracks);
    }

    interface Presenter extends BasePresenter {
        void getGenres(String genre);
    }
}

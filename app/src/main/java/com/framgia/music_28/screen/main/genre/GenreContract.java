package com.framgia.music_28.screen.main.genre;

import com.framgia.music_28.BasePresenter;
import com.framgia.music_28.BaseView;
import com.framgia.music_28.data.model.Genre;

import java.util.ArrayList;

public class GenreContract {

    interface View extends BaseView<Presenter> {
        void displayGenres(ArrayList<Genre> genres);
    }

    interface Presenter extends BasePresenter {
        void getGenres();
    }
}

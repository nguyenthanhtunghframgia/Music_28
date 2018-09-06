package com.framgia.music_28.screen.main.genre;

import android.annotation.SuppressLint;

import com.framgia.music_28.data.model.Genre;
import com.framgia.music_28.data.repository.GenreRepository;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class GenrePresenter implements GenreContract.Presenter {
    private GenreRepository mGenreRepository;
    private GenreContract.View mView;

    @SuppressLint("RestrictedApi")
    public GenrePresenter(GenreRepository genreRepository, GenreContract.View view) {
        mGenreRepository = checkNotNull(genreRepository);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void getGenres() {
        ArrayList<Genre> genres = mGenreRepository.getGenres();
        mView.displayGenres(genres);
    }

    @Override
    public void start() {

    }
}

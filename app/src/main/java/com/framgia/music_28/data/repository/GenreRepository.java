package com.framgia.music_28.data.repository;

import com.framgia.music_28.data.model.Genre;
import com.framgia.music_28.data.source.GenreDataSource;
import com.framgia.music_28.data.source.local.GenresLocalDataSource;

import java.util.ArrayList;

public class GenreRepository implements GenreDataSource {
    private static GenreRepository sInstance;
    private GenresLocalDataSource mGenresLocalDataSource;

    public GenreRepository(GenresLocalDataSource genresLocalDataSource) {
        mGenresLocalDataSource = genresLocalDataSource;
    }

    public static GenreRepository getInstance(GenresLocalDataSource genresLocalDataSource) {
        if (sInstance == null) {
            sInstance = new GenreRepository(genresLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public ArrayList<Genre> getGenres() {
        return mGenresLocalDataSource.getGenres();
    }
}

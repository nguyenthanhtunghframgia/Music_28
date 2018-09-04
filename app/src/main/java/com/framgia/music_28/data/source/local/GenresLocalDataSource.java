package com.framgia.music_28.data.source.local;

import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Genre;
import com.framgia.music_28.data.source.GenreDataSource;

import java.util.ArrayList;

public class GenresLocalDataSource implements GenreDataSource {
    private static GenresLocalDataSource sInstance;

    public GenresLocalDataSource() {
    }

    public static GenresLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new GenresLocalDataSource();
        }
        return sInstance;
    }

    @Override
    public ArrayList<Genre> getGenres() {
        ArrayList<Genre> genres = new ArrayList<>();
        genres.add(new Genre(GenreType.ALL_MUSIC, R.drawable.ic_all_music));
        genres.add(new Genre(GenreType.ALL_AUDIO, R.drawable.ic_all_audio));
        genres.add(new Genre(GenreType.ALTER_NATIVE_ROCK, R.drawable.ic_alter_native_rock));
        genres.add(new Genre(GenreType.AMBIENT, R.drawable.ic_ambient));
        genres.add(new Genre(GenreType.CLASSICAL, R.drawable.ic_classic));
        genres.add(new Genre(GenreType.COUNTRY, R.drawable.ic_country));
        return genres;
    }
}

package com.framgia.music_28.screen.main.genre;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Genre;
import com.framgia.music_28.data.repository.GenreRepository;
import com.framgia.music_28.data.source.local.GenresLocalDataSource;
import com.framgia.music_28.screen.OnItemGenreClickListener;
import com.framgia.music_28.screen.genredetail.GenreDetailActivity;
import com.framgia.music_28.util.Constants;

import java.util.ArrayList;

public class GenreFragment extends Fragment implements GenreContract.View, OnItemGenreClickListener {
    private GenreContract.Presenter mPresenter;

    public GenreFragment() {
    }

    public static GenreFragment newInstance() {
        return new GenreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.genres_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        mPresenter = new GenrePresenter(GenreRepository.getInstance(
                GenresLocalDataSource.getInstance()),
                this);
        mPresenter.getGenres();
    }

    @Override
    public void displayGenres(ArrayList<Genre> genres) {
        RecyclerView recyclerView = getView().findViewById(R.id.genre_recycler_view);
        GenreAdapter genreAdapter = new GenreAdapter(getContext(), genres);
        genreAdapter.setOnItemGenreClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Constants.SPAN_COUNT));
        recyclerView.setAdapter(genreAdapter);
    }

    @Override
    public void setPresenter(GenreContract.Presenter presenter) {

    }

    @Override
    public void onItemGenreClick(String genre) {
        getContext().startActivity(GenreDetailActivity.getGenreDetailIntent(getContext(), genre));
    }
}

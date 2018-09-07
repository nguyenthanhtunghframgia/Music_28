package com.framgia.music_28.screen.genredetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.repository.TrackRepository;
import com.framgia.music_28.data.source.local.TrackLocalDataSource;
import com.framgia.music_28.data.source.remote.TrackRemoteDataSource;

import java.util.ArrayList;

public class GenreDetailActivity extends AppCompatActivity implements GenreDetailContract.View, View.OnClickListener {
    private static final String EXTRA_GENRE_NAME =
            "com.framgia.music_28.screen.genredetail.EXTRA_GENRE_NAME";
    private GenreDetailContract.Presenter mPresenter;
    private ImageButton mButtonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_detail_activity);
        initViews();
        registerEventListener();
    }

    private void registerEventListener() {
        mButtonBack.setOnClickListener(this);
    }

    private void initViews() {
        mButtonBack = findViewById(R.id.button_genre_detail_back);
    }

    public static Intent getGenreDetailIntent(Context context, String genre) {
        Intent intent = new Intent(context, GenreDetailActivity.class);
        intent.putExtra(EXTRA_GENRE_NAME, genre);
        return intent;
    }

    @Override
    public void onStart() {
        super.onStart();
        String genre = getIntent().getStringExtra(EXTRA_GENRE_NAME);
        initData(genre);
    }

    private void initData(String genre) {
        mPresenter = new GenreDetailPresenter(TrackRepository.getInstance(
                TrackLocalDataSource.getInstance(this),
                TrackRemoteDataSource.getInstance()),
                this);
        mPresenter.getGenres(genre);
        TextView mTextTitle = findViewById(R.id.text_genre_detail_title);
        mTextTitle.setText(genre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void displayGenres(ArrayList<Track> tracks) {
        RecyclerView recyclerView = findViewById(R.id.genre_detail_recycler_view);
        GenreDetailAdapter genreDetailAdapter = new GenreDetailAdapter(this, tracks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(genreDetailAdapter);
    }

    @Override
    public void setPresenter(GenreDetailContract.Presenter presenter) {

    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}

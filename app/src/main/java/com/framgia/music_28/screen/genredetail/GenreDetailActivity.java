package com.framgia.music_28.screen.genredetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.repository.TrackRepository;
import com.framgia.music_28.data.source.local.TrackLocalDataSource;
import com.framgia.music_28.data.source.remote.TrackRemoteDataSource;
import com.framgia.music_28.screen.OnItemTrackClickListener;
import com.framgia.music_28.screen.play.PlayActivity;
import com.framgia.music_28.service.MusicPlayerService;

import java.util.ArrayList;

public class GenreDetailActivity extends AppCompatActivity implements GenreDetailContract.View,
        View.OnClickListener,
        OnItemTrackClickListener {
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
    public void displayGenres(ArrayList<Track> tracks) {
        RecyclerView recyclerView = findViewById(R.id.genre_detail_recycler_view);
        GenreDetailAdapter genreDetailAdapter = new GenreDetailAdapter(this, tracks);
        genreDetailAdapter.setOnItemTrackClickListener(this);
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

    @Override
    public void onItemClick(ArrayList<Track> tracks, int position) {
        startService(MusicPlayerService.getServiceIntent(this, tracks, position));
        startActivity(PlayActivity.getPlayIntent(this));
    }
}

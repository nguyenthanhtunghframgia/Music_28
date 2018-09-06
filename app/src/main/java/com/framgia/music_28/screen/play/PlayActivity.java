package com.framgia.music_28.screen.play;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.service.MusicService;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {
    private ImageButton mButtonBack;
    private ImageButton mButtonDownload;
    private TextView mTextTrackName;
    private TextView mTextTrackUser;
    private TextView mTextCurrentTime;
    private TextView mTextDuration;
    private ImageView mImageTrack;
    private SeekBar mSeekBar;
    private ImageButton mButtonRepeat;
    private ImageButton mButtonNext;
    private ImageButton mButtonPlayPause;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonShuffle;
    private boolean mIsBound;
    private MusicService mMusicService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initViews();
        registerEventListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public static Intent getPlayIntent(Context context) {
        Intent intent = new Intent(context, PlayActivity.class);
        return intent;
    }

    private void initViews() {
        mButtonBack = findViewById(R.id.button_back);
        mButtonDownload = findViewById(R.id.button_download);
        mTextTrackName = findViewById(R.id.text_track_play_name);
        mTextTrackUser = findViewById(R.id.text_track_play_user);
        mTextCurrentTime = findViewById(R.id.text_current_time);
        mTextDuration = findViewById(R.id.text_duration);
        mImageTrack = findViewById(R.id.image_track);
        mSeekBar = findViewById(R.id.seek_bar);
        mButtonRepeat = findViewById(R.id.button_repeat);
        mButtonNext = findViewById(R.id.button_next);
        mButtonPlayPause = findViewById(R.id.button_play_pause);
        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonShuffle = findViewById(R.id.button_shuffle);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyServiceBinder binder = (MusicService.MyServiceBinder) service;
            mMusicService = binder.getMusicService();
            showTrack(mMusicService.getTrack());
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    private void showTrack(Track track) {
        mTextTrackName.setText(track.getTitle());
        mTextTrackUser.setText(track.getGenre());
        mTextDuration.setText(String.valueOf(track.getDuration()));
        Glide.with(this).load(track.getArtWorkUrl()).into(mImageTrack);
        mSeekBar.setMax(track.getDuration());
    }

    private void registerEventListener() {
        mButtonBack.setOnClickListener(this);
        mButtonDownload.setOnClickListener(this);
        mButtonShuffle.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
        mButtonPlayPause.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonRepeat.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                doBack();
                break;
            case R.id.button_download:
                doDownload();
                break;
            case R.id.button_shuffle:
                doShuffle();
                break;
            case R.id.button_previous:
                doPrevious();
                break;
            case R.id.button_play_pause:
                doPlayPause();
                break;
            case R.id.button_next:
                doNext();
                break;
            case R.id.button_repeat:
                doRepeat();
                break;
            default:
                break;
        }
    }

    private void doRepeat() {
    }

    private void doNext() {
    }

    private void doPlayPause() {
    }

    private void doPrevious() {
    }

    private void doShuffle() {
    }

    private void doDownload() {
    }

    private void doBack() {
        onBackPressed();
    }
}

package com.framgia.music_28.screen.play;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.service.ButtonState;
import com.framgia.music_28.service.MusicPlayerService;
import com.framgia.music_28.service.MediaPlayerListener;
import com.framgia.music_28.util.Constants;
import com.framgia.music_28.service.MediaStates;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener,
        MediaPlayerListener {
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
    private MusicPlayerService mMusicPlayerService;
    private Handler mHandler;
    private Runnable mRunnable;

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
        Intent intent = new Intent(this, MusicPlayerService.class);
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

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayerService.MyServiceBinder binder = (MusicPlayerService.MyServiceBinder) service;
            mMusicPlayerService = binder.getMusicService();
            mIsBound = true;
            mMusicPlayerService.registerMediaPlayerListener(PlayActivity.this);
            showTrack(mMusicPlayerService.getCurrentTrack());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    private void showTrack(Track track) {
        mTextTrackName.setText(track.getTitle());
        mTextTrackUser.setText(track.getGenre());
        mTextDuration.setText(mMusicPlayerService.getDurationFormat(track.getDuration()));
        Glide.with(this).load(track.getArtWorkUrl()).into(mImageTrack);
        mSeekBar.setMax(track.getDuration());
        updateTime();
    }

    private void updateTime() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress(mMusicPlayerService.getCurrentTime());
                mTextCurrentTime.setText(mMusicPlayerService.
                        getDurationFormat(mMusicPlayerService.getCurrentTime()));
                mHandler.postDelayed(mRunnable, Constants.DELAY_TIME_MILI);
            }
        };
        mHandler.post(mRunnable);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMusicPlayerService.seek(seekBar.getProgress());
    }

    @Override
    public void onTrackChange(Track track) {
        showTrack(track);
    }

    @Override
    public void onShuffleChanged(int shuffleType) {
        mButtonShuffle.setImageLevel(shuffleType);
    }

    @Override
    public void onRepeatChanged(int repeatType) {
        mButtonRepeat.setImageLevel(repeatType);
    }

    @Override
    public void onMediaStateChanged(int mediaState) {
        if (mediaState == MediaStates.PLAYING) {
            mButtonPlayPause.setImageLevel(ButtonState.PAUSED);
        } else if (mediaState == MediaStates.PAUSED) {
            mButtonPlayPause.setImageLevel(ButtonState.PLAYING);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, getResources().getString(R.string.media_error),
                Toast.LENGTH_LONG).show();
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

    private void doNext() {
        mMusicPlayerService.next();
    }

    private void doPrevious() {
        mMusicPlayerService.previous();
    }

    private void doRepeat() {
        mMusicPlayerService.changeRepeat();
    }

    private void doShuffle() {
        mMusicPlayerService.changeShuffle();
    }

    private void doPlayPause() {
        mMusicPlayerService.playTrack();
    }

    private void doDownload() {
        mMusicPlayerService.download();
    }

    private void doBack() {
        onBackPressed();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBound) {
            unbindService(mServiceConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }
}

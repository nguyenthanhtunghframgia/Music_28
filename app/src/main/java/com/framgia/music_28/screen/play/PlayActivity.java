package com.framgia.music_28.screen.play;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.service.MusicService;

public class PlayActivity extends AppCompatActivity {
    private ImageButton mButtonBack;
    private ImageButton mButtonDownload;
    private TextView mTextTrackName;
    private TextView mTextTrackUser;
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

    private void initViews(Track track) {
        mButtonBack = findViewById(R.id.button_back);
        mButtonDownload = findViewById(R.id.button_download);
        mTextTrackName = findViewById(R.id.text_track_play_name);
        mTextTrackUser = findViewById(R.id.text_track_play_user);
        mImageTrack = findViewById(R.id.image_track);
        mSeekBar = findViewById(R.id.seek_bar);
        mButtonRepeat = findViewById(R.id.button_repeat);
        mButtonNext = findViewById(R.id.button_next);
        mButtonPlayPause = findViewById(R.id.button_play_pause);
        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonShuffle = findViewById(R.id.button_shuffle);
        mTextTrackName.setText(track.getTitle());
        mTextTrackUser.setText(track.getGenre());
        Glide.with(this).load(track.getArtWorkUrl()).into(mImageTrack);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyServiceBinder binder = (MusicService.MyServiceBinder) service;
            mMusicService = binder.getMusicService();
            initViews(mMusicService.getTrack());
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };
}

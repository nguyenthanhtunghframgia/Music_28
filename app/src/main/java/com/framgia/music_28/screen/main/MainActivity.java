package com.framgia.music_28.screen.main;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.screen.play.PlayActivity;
import com.framgia.music_28.service.ButtonState;
import com.framgia.music_28.service.MediaPlayerListener;
import com.framgia.music_28.service.MediaStates;
import com.framgia.music_28.service.MusicPlayerService;
import com.framgia.music_28.util.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        MediaPlayerListener {
    private ImageButton mButtonNext;
    private ImageButton mButtonPlayPause;
    private ImageButton mButtonPrevious;
    private TextView mTextTrackName;
    private ImageView mTrackImage;
    private MusicPlayerService mMusicPlayerService;
    private boolean mIsBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        registerListener();
    }

    private void initViews() {
        mButtonNext = findViewById(R.id.button_next);
        mButtonPlayPause = findViewById(R.id.button_play_pause);
        mButtonPrevious = findViewById(R.id.button_previous);
        mTextTrackName = findViewById(R.id.text_track_name);
        mTrackImage = findViewById(R.id.image_track);
        TabLayout mTabLayout = findViewById(R.id.tab_layout);
        ViewPager mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(Constants.PAGE_LIMIT);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this, fragmentManager);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        hideControl();
    }

    private void registerListener() {
        mButtonNext.setOnClickListener(this);
        mButtonPlayPause.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
        mTextTrackName.setOnClickListener(this);
        mTrackImage.setOnClickListener(this);
    }

    public void hideControl() {
        mButtonNext.setVisibility(View.GONE);
        mButtonPlayPause.setVisibility(View.GONE);
        mButtonPrevious.setVisibility(View.GONE);
        mTextTrackName.setVisibility(View.GONE);
        mTrackImage.setVisibility(View.GONE);
    }

    public void showControl() {
        mButtonNext.setVisibility(View.VISIBLE);
        mButtonPlayPause.setVisibility(View.VISIBLE);
        mButtonPrevious.setVisibility(View.VISIBLE);
        mTextTrackName.setVisibility(View.VISIBLE);
        mTrackImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_track:
                doExpand();
                break;
            case R.id.text_track_name:
                doExpand();
                break;
            case R.id.button_play_pause:
                doPlayPause();
                break;
            case R.id.button_next:
                doNext();
                break;
            case R.id.button_previous:
                doPrevious();
                break;
            default:
                break;
        }
    }

    private void doPrevious() {
        if (mIsBound) {
            mMusicPlayerService.previous();
        }
    }

    private void doNext() {
        if (mIsBound) {
            mMusicPlayerService.next();
        }
    }

    private void doPlayPause() {
        if (mIsBound) {
            mMusicPlayerService.playTrack();
        }
    }

    private void doExpand() {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isMyServiceRunning(MusicPlayerService.class)) {
            showControl();
            Intent intent = new Intent(this, MusicPlayerService.class);
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayerService.MyServiceBinder binder = (MusicPlayerService.MyServiceBinder) service;
            mMusicPlayerService = binder.getMusicService();
            mIsBound = true;
            mMusicPlayerService.registerMediaPlayerListener(MainActivity.this);
            showTrack(mMusicPlayerService.getCurrentTrack());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    private void showTrack(Track currentTrack) {
        Glide.with(getApplicationContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.ic_default_small_image)
                        .error(R.drawable.ic_default_small_image))
                .load(currentTrack.getArtWorkUrl())
                .into(mTrackImage);
        mTextTrackName.setText(currentTrack.getTitle());
    }

    @Override
    public void onTrackChange(Track track) {
        showTrack(track);
    }

    @Override
    public void onShuffleChanged(int shuffleType) {
    }

    @Override
    public void onRepeatChanged(int repeatType) {
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
    protected void onStop() {
        super.onStop();
        if (mIsBound) {
            unbindService(mServiceConnection);
            mIsBound = false;
        }
    }
}

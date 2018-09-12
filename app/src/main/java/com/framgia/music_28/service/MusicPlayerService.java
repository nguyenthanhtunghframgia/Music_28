package com.framgia.music_28.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.screen.play.PlayActivity;
import com.framgia.music_28.util.Constants;
import com.framgia.music_28.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.framgia.music_28.service.MediaStates.IDLE;
import static com.framgia.music_28.service.MediaStates.PAUSED;
import static com.framgia.music_28.service.MediaStates.PLAYING;
import static com.framgia.music_28.service.RepeatType.ALL;
import static com.framgia.music_28.service.RepeatType.NONE;
import static com.framgia.music_28.service.RepeatType.ONE;
import static com.framgia.music_28.service.ShuffleType.OFF;
import static com.framgia.music_28.service.ShuffleType.ON;

public class MusicPlayerService extends Service implements ISubject {
    public static final String EXTRA_TRACK_POSITION =
            "com.framgia.music_28.service.EXTRA_TRACK_POSITION";
    public static final String EXTRA_TRACKS =
            "com.framgia.music_28.service.EXTRA_TRACKS";
    public IBinder mIBinder = new MyServiceBinder();
    private ArrayList<Track> mTracks;
    private MediaPlayer mPlayer;
    private int mCurrentTrack;
    private Random mRandom;
    private int mShuffle;
    private int mRepeat;
    private int mState = MediaStates.IDLE;
    private List<MediaPlayerListener> mListeners;
    private String mTextTitle;
    private String mTextUser;
    private String mUri;

    public class MyServiceBinder extends Binder {
        public MusicPlayerService getMusicService() {
            return MusicPlayerService.this;
        }
    }

    public static Intent getServiceIntent(Context context, ArrayList<Track> tracks, int position) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.putExtra(EXTRA_TRACK_POSITION, position);
        intent.putParcelableArrayListExtra(EXTRA_TRACKS, tracks);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRandom = new Random();
        mPlayer = new MediaPlayer();
        mListeners = new ArrayList<>();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        initMediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == null) {
            mTracks = intent.getParcelableArrayListExtra(EXTRA_TRACKS);
            mCurrentTrack = intent.getIntExtra(EXTRA_TRACK_POSITION, 0);
            changeState(IDLE);
            playTrack();
            return START_NOT_STICKY;
        } else {
            switch (intent.getAction()) {
                case Constants.ACTION_PLAY:
                    playTrack();
                    break;
                case Constants.ACTION_NEXT:
                    next();
                    break;
                case Constants.ACTION_PREVIOUS:
                    previous();
                    break;
                case Constants.ACTION_DISMISS:
                    stopForeground(true);
                    stopSelf();
                    break;
                default:
                    break;
            }
            return START_STICKY;
        }
    }

    private final MediaPlayer.OnPreparedListener mOnPreparedListener =
            new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPlayer.start();
                }
            };

    private final MediaPlayer.OnCompletionListener mOnCompletionListener =
            new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    changeState(IDLE);
                    if (mRepeat == RepeatType.ONE) {
                        mPlayer.start();
                        return;
                    }
                    if (mRepeat == RepeatType.ALL && mCurrentTrack == mTracks.size() - 1) {
                        changeTrack(0);
                        playTrack();
                        return;
                    }
                    next();
                }
            };

    private final MediaPlayer.OnErrorListener mOnErrorListener =
            new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mPlayer.reset();
                    notifyError();
                    return false;
                }
            };

    private void initMediaPlayer() {
        mPlayer.setOnPreparedListener(mOnPreparedListener);
        mPlayer.setOnCompletionListener(mOnCompletionListener);
        mPlayer.setOnErrorListener(mOnErrorListener);
    }

    public void playTrack() {
        try {
            if (mState == MediaStates.IDLE) {
                if (mPlayer != null) {
                    mPlayer.reset();
                }
                Track track = mTracks.get(mCurrentTrack);
                mTextTitle = track.getTitle();
                mTextUser = track.getGenre();
                mUri = track.getArtWorkUrl();
                if (mUri != null) {
                    mPlayer.setDataSource(StringUtils.getStreamTrackAPI(String.valueOf(track.getId())));
                } else {
                    mPlayer.setDataSource(track.getDownloadUrl());
                }
                mPlayer.prepareAsync();
                changeState(PLAYING);
                showNotification();
                return;
            }
            if (mState == PLAYING) {
                pause();
                showNotification();
                return;
            }
            if (mState == MediaStates.PAUSED) {
                mPlayer.start();
                changeState(PLAYING);
                showNotification();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mPlayer.pause();
        changeState(PAUSED);
    }

    public void next() {
        if (getShuffle() == ShuffleType.ON) {
            mCurrentTrack = mRandom.nextInt(mTracks.size() - 1);
            doPlay();
            return;
        }
        if (mCurrentTrack == mTracks.size() - 1) {
            mCurrentTrack = 0;
            doPlay();
            return;
        }
        mCurrentTrack++;
        doPlay();
    }

    public void previous() {
        if (getShuffle() == ShuffleType.ON) {
            mCurrentTrack = mRandom.nextInt(mTracks.size() - 1);
            doPlay();
            return;
        }
        if (mCurrentTrack == 0) {
            mCurrentTrack = mTracks.size() - 1;
            doPlay();
            return;
        }
        mCurrentTrack--;
        doPlay();
    }

    private void doPlay() {
        changeTrack(mCurrentTrack);
        changeState(IDLE);
        playTrack();
    }

    public void changeShuffle() {
        switch (mShuffle) {
            case OFF:
                mShuffle = ShuffleType.ON;
                break;
            case ON:
                mShuffle = ShuffleType.OFF;
                break;
        }
        setShuffle(mShuffle);
    }

    public int getShuffle() {
        return mShuffle;
    }

    public void setShuffle(int shuffle) {
        mShuffle = shuffle;
        notifyShuffleChanged();
    }

    private void notifyShuffleChanged() {
        for (MediaPlayerListener listener : mListeners) {
            listener.onShuffleChanged(mShuffle);
        }
    }

    public void changeRepeat() {
        switch (mRepeat) {
            case NONE:
                mRepeat = ONE;
                break;
            case ONE:
                mRepeat = ALL;
                break;
            case ALL:
                mRepeat = NONE;
                break;
        }
        setRepeat(mRepeat);
    }

    public int getRepeat() {
        return mRepeat;
    }

    public void setRepeat(@RepeatType int repeat) {
        mRepeat = repeat;
        notifyRepeatChanged();
    }

    private void notifyRepeatChanged() {
        for (MediaPlayerListener listener : mListeners) {
            listener.onRepeatChanged(mRepeat);
        }
    }

    public void changeState(int state) {
        mState = state;
        notifyStateChange();
    }

    public int getState() {
        return mState;
    }

    private void notifyStateChange() {
        for (MediaPlayerListener listener : mListeners) {
            listener.onMediaStateChanged(mState);
        }
    }

    public void changeTrack(int position) {
        mCurrentTrack = position;
        notifyTrackChange();
    }

    public Track getCurrentTrack() {
        return mTracks.get(mCurrentTrack);
    }

    private void notifyTrackChange() {
        for (MediaPlayerListener listener : mListeners) {
            listener.onTrackChange(getCurrentTrack());
        }
    }

    private void notifyError() {
        for (MediaPlayerListener listener : mListeners) {
            listener.onError();
        }
    }

    @Override
    public void registerMediaPlayerListener(MediaPlayerListener listener) {
        if (listener == null || mListeners.indexOf(listener) != -1) {
            return;
        }
        mListeners.add(listener);
    }

    @Override
    public void unregisterMediaPlayerListener(MediaPlayerListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void removeMediaPlayerListener() {
        mListeners.clear();
    }

    public int getCurrentTime() {
        return mPlayer.getCurrentPosition();
    }

    public void seek(int progress) {
        mPlayer.seekTo(progress);
    }

    public String getDurationFormat(int duration) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(StringUtils.DURATION_FORMAT);
        return dateFormat.format(new Date(duration));
    }

    public void showNotification() {
        Intent notificationIntent = new Intent(this, PlayActivity.class);
        notificationIntent.setAction(Constants.ACTION_MAIN);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, notificationIntent, 0);

        Intent previousIntent = new Intent(getApplicationContext(), MusicPlayerService.class);
        previousIntent.setAction(Constants.ACTION_PREVIOUS);
        PendingIntent previousPendingIntent = PendingIntent.getService(getApplicationContext(),
                0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(getApplicationContext(), MusicPlayerService.class);
        playIntent.setAction(Constants.ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getService(getApplicationContext(),
                0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(getApplicationContext(), MusicPlayerService.class);
        nextIntent.setAction(Constants.ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getService(getApplicationContext(),
                0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissIntent = new Intent(getApplicationContext(), MusicPlayerService.class);
        dismissIntent.setAction(Constants.ACTION_DISMISS);
        PendingIntent dismissPendingIntent = PendingIntent.getService(getApplicationContext(),
                0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custome_notification);
        remoteViews.setTextViewText(R.id.small_track_name, mTextTitle);
        remoteViews.setTextViewText(R.id.small_track_user, mTextUser);
        remoteViews.setImageViewResource(R.id.small_image_track, R.drawable.ic_default_small_image);
        remoteViews.setImageViewResource(R.id.small_track_previous, R.drawable.ic_skip_previous);
        if (mPlayer.isPlaying()) {
            remoteViews.setImageViewResource(R.id.small_track_play_pause,
                    R.drawable.ic_pause_circle_outline);
        } else {
            remoteViews.setImageViewResource(R.id.small_track_play_pause, R.drawable.ic_play_arrow);
        }
        remoteViews.setImageViewResource(R.id.small_track_next, R.drawable.ic_skip_next);
        remoteViews.setImageViewResource(R.id.small_clear, R.drawable.ic_clear);
        remoteViews.setOnClickPendingIntent(R.id.small_track_previous, previousPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.small_track_play_pause, playPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.small_track_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.small_clear, dismissPendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), null);
        builder.setSmallIcon(R.drawable.ic_default_small_image)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews);

        Notification notification = builder.build();
        startForeground(Constants.ID_FOREGROUND_SERVICE, notification);
    }

    @Override
    public void onDestroy() {
        mPlayer.release();
        stopSelf();
        super.onDestroy();
    }
}

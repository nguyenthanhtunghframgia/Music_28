package com.framgia.music_28.screen.main.download;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.util.Constants;
import com.framgia.music_28.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {
    private static final String EXTRA_TRACK = "com.framgia.music_28.screen.main.download.EXTRA_TRACK";
    private static final String EXTRA_RECEIVER = "com.framgia.music_28.screen.main.download.EXTRA_RECEIVER";
    public static final int RESULT_OK = 1;
    public static final String EXTRA_FAIL = "com.framgia.music_28.screen.main.download.EXTRA_FAIL";
    public static final int RESULT_FAIL = 0;

    public DownloadService() {
        super(null);
    }

    public static Intent getDownloadServiceIntent(Context context, Track track, DownloadResultReceiver downloadResultReceiver) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(EXTRA_RECEIVER, downloadResultReceiver);
        intent.putExtra(EXTRA_TRACK, track);
        return intent;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        Track track = intent.getParcelableExtra(EXTRA_TRACK);
        ResultReceiver resultReceiver = intent.getParcelableExtra(EXTRA_RECEIVER);
        String downloadUrl = StringUtils.getStreamTrackAPI(String.valueOf(track.getId()));
        String name = track.getTitle();
        if (isExternalStorageWritable()) {
            downloadSong(resultReceiver, downloadUrl, name);
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void downloadSong(ResultReceiver resultReceiver, String urlSong, String title) {
        String destPath = StringUtils.getDownloadPath();
        Bundle bundle = new Bundle();
        File file = new File(destPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            String outputPath = title.concat(Constants.FILE_EXTEND);
            File outputFile = new File(destPath, outputPath);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            URL url = new URL(urlSong);
            HttpURLConnection originalUrl = (HttpURLConnection) url.openConnection();
            originalUrl.setInstanceFollowRedirects(false);
            URL directUrl = new URL(originalUrl.getHeaderField(Constants.HEADER_FIELD));
            URLConnection connection = directUrl.openConnection();
            connection.connect();
            connection.getURL();
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(outputFile);
            byte data[] = new byte[Constants.BUFF_SIZE];
            int length;
            while ((length = input.read(data)) != -1) {
                output.write(data, 0, length);
            }
            output.flush();
            output.close();
            input.close();
            resultReceiver.send(RESULT_OK, bundle);
        } catch (IOException e) {
            bundle.putParcelable(EXTRA_FAIL, (Parcelable) e);
            resultReceiver.send(RESULT_FAIL, bundle);
        }
    }
}

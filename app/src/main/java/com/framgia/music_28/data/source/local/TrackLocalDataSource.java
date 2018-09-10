package com.framgia.music_28.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.model.TrackBuilder;
import com.framgia.music_28.data.source.TrackDataSource;
import com.framgia.music_28.util.Constants;

import java.util.ArrayList;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private static TrackLocalDataSource sInstance;
    private Context mContext;

    public TrackLocalDataSource(Context context) {
        mContext = context;
    }

    public static TrackLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public ArrayList<Track> getTracks() {
        ArrayList<Track> tracks = new ArrayList<>();
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String projections[] = new String[]{
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.DATA
        };
        String mSelectionClause = MediaStore.Audio.Media.DATA.concat(Constants.QUERY_KEY);
        String[] mSelectionArgs = {Constants.FOLDER_PATH};
        Cursor cursor = mContext.getContentResolver().query(audioUri, projections,
                mSelectionClause, mSelectionArgs, null);
        cursor.moveToFirst();
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);

        String name, album, path;
        int duration;
        while (!cursor.isAfterLast()) {
            name = cursor.getString(indexTitle);
            album = cursor.getString(indexAlbum);
            path = cursor.getString(indexData);
            duration = cursor.getInt(indexDuration);
            TrackBuilder trackBuilder = new TrackBuilder()
                    .setDuration(duration)
                    .setGenre(album)
                    .setTitle(name)
                    .setDownloadUrl(path);
            Track track = trackBuilder.getTrack();
            tracks.add(track);
            cursor.moveToNext();
        }
        cursor.close();
        return tracks;
    }
}

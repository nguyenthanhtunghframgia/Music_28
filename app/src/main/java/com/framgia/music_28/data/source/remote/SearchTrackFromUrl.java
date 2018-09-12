package com.framgia.music_28.data.source.remote;

import android.os.AsyncTask;

import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.model.TrackBuilder;
import com.framgia.music_28.data.source.TrackDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchTrackFromUrl extends AsyncTask<String, Void, ArrayList<Track>> {
    private TrackDataSource.RemoteDataSource.OnFetchDataListener mOnFetchDataListener;
    private Exception mException;

    public SearchTrackFromUrl(TrackDataSource.RemoteDataSource.OnFetchDataListener onFetchDataListener) {
        mOnFetchDataListener = onFetchDataListener;
    }

    @Override
    protected ArrayList<Track> doInBackground(String... strings) {
        String url = strings[0];
        String data;
        try {
            data = getJsonString(url);
            return getTrackFromJSon(data);
        } catch (JSONException e) {
            mException = e;
        } catch (IOException e) {
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Track> tracks) {
        super.onPostExecute(tracks);
        if (mException == null) {
            mOnFetchDataListener.onSuccess(tracks);
        } else {
            mOnFetchDataListener.onError(mException);
        }
    }

    private String getJsonString(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        connection.disconnect();
        return stringBuilder.toString();
    }

    public ArrayList<Track> getTrackFromJSon(String data) throws JSONException {
        ArrayList<Track> tracks = new ArrayList<>();
        JSONObject object = new JSONObject(data);
        JSONArray jsonArray = object.getJSONArray(Track.JsonKey.COLLECTION);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            TrackBuilder trackBuilder = new TrackBuilder()
                    .setArtWorkUrl(jsonObject.optString(Track.JsonKey.ARTWORK_URL))
                    .setDownloadable(jsonObject.getBoolean(Track.JsonKey.DOWNLOADABLE))
                    .setDownloadUrl(jsonObject.optString(Track.JsonKey.DOWNLOAD_URL))
                    .setDuration(jsonObject.getInt(Track.JsonKey.DURATION))
                    .setGenre(jsonObject.optString(Track.JsonKey.GENRE))
                    .setId(jsonObject.getInt(Track.JsonKey.ID))
                    .setTitle(jsonObject.optString(Track.JsonKey.TITLE))
                    .setUri(jsonObject.optString(Track.JsonKey.URI));
            Track track = trackBuilder.getTrack();
            tracks.add(track);
        }
        return tracks;
    }
}

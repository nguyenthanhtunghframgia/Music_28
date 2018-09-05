package com.framgia.music_28.screen.main.tracks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;

import java.util.ArrayList;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksHolder> {
    private ArrayList<Track> mTracks;
    private Context mContext;

    public TracksAdapter(Context context, ArrayList<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    @NonNull
    @Override
    public TracksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_track, viewGroup, false);
        return new TracksHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksHolder tracksHolder, int i) {
        tracksHolder.bindData(mTracks.get(i));
    }

    @Override
    public int getItemCount() {
        return mTracks.size() != 0 ? mTracks.size() : 0;
    }

    public static class TracksHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mImageTrack;
        private TextView mTextTrackName;
        private TextView mTextTrackUser;

        public TracksHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;
            mImageTrack = itemView.findViewById(R.id.image_item_track);
            mTextTrackName = itemView.findViewById(R.id.text_item_track_name);
            mTextTrackUser = itemView.findViewById(R.id.text_item_track_user);
        }

        private void bindData(Track track) {
            Glide.with(mContext).load(track.getArtWorkUrl()).into(mImageTrack);
            mTextTrackName.setText(track.getTitle());
            mTextTrackUser.setText(track.getGenre());
        }
    }
}

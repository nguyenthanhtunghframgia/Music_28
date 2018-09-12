package com.framgia.music_28.screen.genredetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.screen.OnItemTrackClickListener;

import java.util.ArrayList;

public class GenreDetailAdapter extends RecyclerView.Adapter<GenreDetailAdapter.GenreDetailHolder> {
    private ArrayList<Track> mTracks;
    private Context mContext;
    private OnItemTrackClickListener mOnItemTrackClickListener;


    public GenreDetailAdapter(Context context, ArrayList<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    public void setOnItemTrackClickListener(OnItemTrackClickListener onItemTrackClickListener) {
        mOnItemTrackClickListener = onItemTrackClickListener;
    }

    @NonNull
    @Override
    public GenreDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_track, viewGroup, false);
        return new GenreDetailHolder(view, mContext, mTracks, mOnItemTrackClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreDetailHolder genreDetailHolder, int i) {
        genreDetailHolder.bindData(mTracks.get(i));
    }

    @Override
    public int getItemCount() {
        return mTracks.size() != 0 ? mTracks.size() : 0;
    }

    public static class GenreDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private ArrayList<Track> mTracks;
        private ImageView mImageTrack;
        private TextView mTextTrackName;
        private TextView mTextTrackUser;
        private OnItemTrackClickListener mOnItemTrackClickListener;

        public GenreDetailHolder(@NonNull View itemView, Context context, ArrayList<Track> tracks,
                                 OnItemTrackClickListener onItemTrackClickListener) {
            super(itemView);
            mContext = context;
            mTracks = tracks;
            mOnItemTrackClickListener = onItemTrackClickListener;
            mImageTrack = itemView.findViewById(R.id.image_item_track);
            mTextTrackName = itemView.findViewById(R.id.text_item_track_name);
            mTextTrackUser = itemView.findViewById(R.id.text_item_track_user);
            itemView.setOnClickListener(this);
        }

        private void bindData(Track track) {
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_default_small_image)
                            .error(R.drawable.ic_default_small_image))
                    .load(track.getArtWorkUrl())
                    .into(mImageTrack);
            mTextTrackName.setText(track.getTitle());
            mTextTrackUser.setText(track.getGenre());
        }

        @Override
        public void onClick(View v) {
            mOnItemTrackClickListener.onItemClick(mTracks, getAdapterPosition());
        }
    }
}

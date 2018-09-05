package com.framgia.music_28.screen.main.tracks;

import com.framgia.music_28.data.model.Track;

import java.util.ArrayList;

public interface OnItemTrackClickListener {
    void onItemClick(ArrayList<Track> tracks, int position);
}

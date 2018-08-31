package com.framgia.music_28.screen.main.tracks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_28.R;

public class TracksFragment extends Fragment {

    public TracksFragment() {
    }

    public static TracksFragment newInstance() {
        return new TracksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_track_fragment, container, false);
    }
}

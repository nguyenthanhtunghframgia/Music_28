package com.framgia.music_28.screen.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_28.R;

public class AllTrackFragment extends Fragment {

    public AllTrackFragment() {
    }

    public static AllTrackFragment newInstance() {
        return new AllTrackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_track_fragment, container, false);
    }
}

package com.framgia.music_28.screen.main.tracks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_28.R;
import com.framgia.music_28.data.model.Track;
import com.framgia.music_28.data.repository.TrackRepository;
import com.framgia.music_28.data.source.local.TrackLocalDataSource;
import com.framgia.music_28.data.source.remote.TrackRemoteDataSource;

import java.util.ArrayList;

public class TracksFragment extends Fragment implements TracksContract.View {
    private TracksContract.Presenter mPresenter;

    public TracksFragment() {
    }

    public static TracksFragment newInstance() {
        return new TracksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_track_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        mPresenter = new TracksPresenter(TrackRepository.getInstance(
                TrackLocalDataSource.getInstance(getContext()),
                TrackRemoteDataSource.getInstance()),
                this);
        mPresenter.getTracks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void displayTracks(ArrayList<Track> tracks) {
        RecyclerView recyclerView = getView().findViewById(R.id.tracks_recycler_view);
        TracksAdapter tracksAdapter = new TracksAdapter(getContext(), tracks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(tracksAdapter);
    }

    @Override
    public void setPresenter(TracksContract.Presenter presenter) {

    }
}

package com.framgia.music_28.screen.main.download;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.framgia.music_28.screen.OnItemTrackClickListener;
import com.framgia.music_28.screen.play.PlayActivity;
import com.framgia.music_28.service.MusicPlayerService;
import com.framgia.music_28.util.Constants;

import java.util.ArrayList;

public class DownloadFragment extends Fragment implements DownloadContract.View,
        OnItemTrackClickListener {
    private DownloadContract.Presenter mPresenter;

    public DownloadFragment() {
    }

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.download_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestDownloadPermission();
    }

    private void initData() {
        mPresenter = new DownloadPresenter(TrackRepository.getInstance(
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
        RecyclerView recyclerView = getView().findViewById(R.id.download_recycler_view);
        DownloadAdapter downloadAdapter = new DownloadAdapter(getContext(), tracks);
        downloadAdapter.setOnItemTrackClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(downloadAdapter);
    }

    @Override
    public void setPresenter(DownloadContract.Presenter presenter) {

    }

    @Override
    public void onItemClick(ArrayList<Track> tracks, int position) {
        getContext().startService(MusicPlayerService.getServiceIntent(getContext(), tracks, position));
        getContext().startActivity(PlayActivity.getPlayIntent(getContext()));
    }


    private void requestDownloadPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

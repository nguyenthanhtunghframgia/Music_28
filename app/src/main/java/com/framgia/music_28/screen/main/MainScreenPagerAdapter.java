package com.framgia.music_28.screen.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.framgia.music_28.R;
import com.framgia.music_28.screen.main.fragment.AllTrackFragment;
import com.framgia.music_28.screen.main.fragment.DownloadFragment;
import com.framgia.music_28.screen.main.fragment.GenresFragment;

public class MainScreenPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUMBER_OF_FRAGMENT = 3;
    private Context mContext;

    public MainScreenPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new AllTrackFragment();
                break;
            case 1:
                fragment = new GenresFragment();
                break;
            case 2:
                fragment = new DownloadFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = mContext.getResources().getString(R.string.all_track_fragment_title);
                break;
            case 1:
                title = mContext.getResources().getString(R.string.genres_fragment_title);
                break;
            case 2:
                title = mContext.getResources().getString(R.string.download_track_fragment_title);
                break;
        }
        return title;
    }
}

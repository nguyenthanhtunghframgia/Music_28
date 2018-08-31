package com.framgia.music_28.screen.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.framgia.music_28.R;
import com.framgia.music_28.screen.main.download.DownloadFragment;
import com.framgia.music_28.screen.main.genre.GenreFragment;
import com.framgia.music_28.screen.main.tracks.TracksFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_COUNT = 3;
    private Context mContext;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case TabType.ALL:
                return TracksFragment.newInstance();
            case TabType.GENRE:
                return GenreFragment.newInstance();
            case TabType.DOWNLOAD:
                return DownloadFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case TabType.ALL:
                title = mContext.getResources().getString(R.string.tracks_fragment_title);
                break;
            case TabType.GENRE:
                title = mContext.getResources().getString(R.string.genre_fragment_title);
                break;
            case TabType.DOWNLOAD:
                title = mContext.getResources().getString(R.string.download_fragment_title);
                break;
        }
        return title;
    }
}

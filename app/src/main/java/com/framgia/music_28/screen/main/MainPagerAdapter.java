package com.framgia.music_28.screen.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.framgia.music_28.R;
import com.framgia.music_28.screen.main.fragment.AllTrackFragment;
import com.framgia.music_28.screen.main.fragment.DownloadFragment;
import com.framgia.music_28.screen.main.fragment.GenreFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public static final int TAB_COUNT = 3;
    private Context mContext;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case TabType.ALL:
                return AllTrackFragment.newInstance();
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
                title = mContext.getResources().getString(R.string.all_track_fragment_title);
                break;
            case TabType.GENRE:
                title = mContext.getResources().getString(R.string.genres_fragment_title);
                break;
            case TabType.DOWNLOAD:
                title = mContext.getResources().getString(R.string.download_track_fragment_title);
                break;
        }
        return title;
    }
}

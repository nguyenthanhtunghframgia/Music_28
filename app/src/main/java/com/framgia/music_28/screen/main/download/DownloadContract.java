package com.framgia.music_28.screen.main.download;

import com.framgia.music_28.BasePresenter;
import com.framgia.music_28.BaseView;
import com.framgia.music_28.data.model.Track;

import java.util.ArrayList;

public class DownloadContract {
    interface View extends BaseView<DownloadContract.Presenter> {
        void displayTracks(ArrayList<Track> tracks);
    }

    interface Presenter extends BasePresenter {
        void getTracks();
    }
}

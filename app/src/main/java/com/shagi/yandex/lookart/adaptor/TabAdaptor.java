package com.shagi.yandex.lookart.adaptor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.shagi.yandex.lookart.fragment.RecyclerArtistFragment;
import com.shagi.yandex.lookart.fragment.SelectedArtistFragment;

/**
 * Created by Shagi on 06.04.2016.
 */
public class TabAdaptor extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public static final int ARTISTS_FRAGMENT_POSITION = 0;
    public static final int SELECTED_ARTIST_POSITION = 1;

    private RecyclerArtistFragment artistsFragment;
    private SelectedArtistFragment selectedArtistFragment;

    public TabAdaptor(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        artistsFragment = new RecyclerArtistFragment();
        selectedArtistFragment = new SelectedArtistFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return artistsFragment;
            case 1:
                return selectedArtistFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

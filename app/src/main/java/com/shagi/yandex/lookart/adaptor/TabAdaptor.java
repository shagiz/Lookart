package com.shagi.yandex.lookart.adaptor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.shagi.yandex.lookart.fragment.ArtistsFragment;
import com.shagi.yandex.lookart.fragment.SelectedArtistFragment;

/**
 * Created by Shagi on 06.04.2016.
 */
public class TabAdaptor extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public TabAdaptor(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs=numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ArtistsFragment();
            case 1:
                return new SelectedArtistFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}

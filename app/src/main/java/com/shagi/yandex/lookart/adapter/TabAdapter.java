package com.shagi.yandex.lookart.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.shagi.yandex.lookart.fragment.RecyclerArtistFragment;
import com.shagi.yandex.lookart.fragment.SelectedArtistFragment;

/**
 * Создает фрагменты RecyclerArtistFragment и SelectedArtistFragment,
 * возвращает их в зависимости от текущей позиции TabLayout.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public static final int ARTISTS_FRAGMENT_POSITION = 0;
    public static final int SELECTED_ARTIST_POSITION = 1;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ARTISTS_FRAGMENT_POSITION:
                return new RecyclerArtistFragment();
            case SELECTED_ARTIST_POSITION:
                return new SelectedArtistFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

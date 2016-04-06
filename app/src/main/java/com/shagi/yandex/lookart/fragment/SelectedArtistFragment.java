package com.shagi.yandex.lookart.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shagi.yandex.lookart.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedArtistFragment extends Fragment {


    public SelectedArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_artist, container, false);
    }

}

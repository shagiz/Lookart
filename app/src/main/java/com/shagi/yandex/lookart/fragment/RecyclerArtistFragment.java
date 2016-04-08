package com.shagi.yandex.lookart.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.shagi.yandex.lookart.R;
import com.shagi.yandex.lookart.adaptor.ListArtistAdapter;
import com.shagi.yandex.lookart.pojo.Artist;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerArtistFragment extends ArtistFragment {

    OnArtistSelectedListener onArtistSelectedListener;

    public interface OnArtistSelectedListener{
        void onArtistSelected(int position,boolean openInfoTab);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_artists, container, false);

        RecyclerView rvArtists = (RecyclerView) rootView.findViewById(R.id.rvArtists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        onArtistSelectedListener = (OnArtistSelectedListener) getActivity();

        rvArtists.setLayoutManager(layoutManager);
        rvArtists.setHasFixedSize(true);
        rvArtists.setItemAnimator(new DefaultItemAnimator());

        artists = loadArtistModels();

        final ListArtistAdapter artistAdapter = new ListArtistAdapter(artists);

        rvArtists.setAdapter(artistAdapter);

        artistAdapter.setOnItemClickListener(new ListArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onArtistSelectedListener.onArtistSelected(position, true);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}

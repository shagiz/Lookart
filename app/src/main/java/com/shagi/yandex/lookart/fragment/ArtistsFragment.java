package com.shagi.yandex.lookart.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shagi.yandex.lookart.JsonHelper;
import com.shagi.yandex.lookart.MainActivity;
import com.shagi.yandex.lookart.R;
import com.shagi.yandex.lookart.adaptor.ArtistsAdapter;
import com.shagi.yandex.lookart.pojo.Artist;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {

    private RecyclerView rvArtists;
    private RecyclerView.LayoutManager layoutManager;

    private ArtistsAdapter adapter;

    private List<Artist> artists;

    public MainActivity activity;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }
        loadArtistModels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_artists, container, false);

        rvArtists = (RecyclerView) rootView.findViewById(R.id.rvArtists);

        layoutManager = new LinearLayoutManager(getActivity());

        rvArtists.setLayoutManager(layoutManager);
        artists = loadArtistModels();
        Log.d("TEST","До");
        adapter = new ArtistsAdapter(artists);
        Log.d("TEST","После");

        rvArtists.setAdapter(adapter);
        Log.d("TEST", "После setAdapter");

        // Inflate the layout for this fragment
        return rootView;
    }

    private List<Artist> loadArtistModels() {
        try {
            return new JsonHelper().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

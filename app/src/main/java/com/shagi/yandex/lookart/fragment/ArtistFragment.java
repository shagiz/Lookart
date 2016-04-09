package com.shagi.yandex.lookart.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shagi.yandex.lookart.JsonHelper;
import com.shagi.yandex.lookart.MainActivity;
import com.shagi.yandex.lookart.pojo.Artist;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shagi on 08.04.2016.
 */
public abstract class ArtistFragment extends Fragment {

    protected List<Artist> artists;
    protected MainActivity activity;

    public ArtistFragment(){

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }
        if (artists==null){
            artists=loadArtistModels();
        }
    }

    protected List<Artist> loadArtistModels() {
        try {
            return new JsonHelper().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Artist> getArtists() {
        return artists;
    }
}

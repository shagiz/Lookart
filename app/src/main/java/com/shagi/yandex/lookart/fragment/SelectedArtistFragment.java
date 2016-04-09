package com.shagi.yandex.lookart.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shagi.yandex.lookart.DownloadImageTask;
import com.shagi.yandex.lookart.R;




/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedArtistFragment extends ArtistFragment {

    private ImageView ivBigCover;
    private TextView tvInfoStyle;
    private TextView tvInfoAlbums;
    private TextView tvInfo;

    public SelectedArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selected_artist, container, false);
        ivBigCover = (ImageView) rootView.findViewById(R.id.bigCover);
        tvInfoStyle = (TextView) rootView.findViewById(R.id.tvInfoStyle);
        tvInfoAlbums = (TextView) rootView.findViewById(R.id.tvInfoAlbums);
        tvInfo = (TextView) rootView.findViewById(R.id.tvInfo);
        tvInfo.setMovementMethod(new ScrollingMovementMethod());

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.onArtistSelected(0,false);
    }

    public void changeInfo(int position){
        //new DownloadImageTask(ivBigCover).execute(artists.get(position).getCover().getBig());

        ImageLoader.getInstance().displayImage(artists.get(position).getCover().getBig(), ivBigCover);

        String style = "";
        for (String string : artists.get(position).getGenres()) {
            style += string + " ";
        }
        tvInfoStyle.setText(style);
        String albums = "альбомов " + artists.get(position).getAlbums() + ", треков " + artists.get(position).getTracks();
        tvInfoAlbums.setText(albums);
        tvInfo.setText(artists.get(position).getDescription());
    }

}

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
import com.shagi.yandex.lookart.MainActivity;
import com.shagi.yandex.lookart.R;
import com.shagi.yandex.lookart.pojo.Artist;


/**
 * Иницализирует и заполняет фрагмент выбранного исполнителя
 */
public class SelectedArtistFragment extends Fragment {

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
        // Установить значение 2 табы данными 1-ого элемента списка
        ((MainActivity)getActivity()).onArtistSelected(0,false);
    }

    /**
     * Заполняет все View фрагмента данными выбранного исполнителя
     *
     * @param position Выбранный элемент списка исполнителей
     */
    public void changeInfo(int position){
        Artist artist = ((MainActivity)getActivity()).getArtists().get(position);
        ImageLoader.getInstance().displayImage(artist.getCover().getBig(), ivBigCover);

        String style = "";
        for (String string : artist.getGenres()) {
            style += string + " ";
        }
        tvInfoStyle.setText(style);
        String albums = "альбомов " + artist.getAlbums() + ", треков " + artist.getTracks();
        tvInfoAlbums.setText(albums);
        if (artist.getLink()!=null){
            tvInfo.setText(artist.getLink()+"\n\n"+artist.getDescription());
        }else {
            tvInfo.setText(artist.getDescription());
        }
    }

}

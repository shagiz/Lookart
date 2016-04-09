package com.shagi.yandex.lookart.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shagi.yandex.lookart.MainActivity;
import com.shagi.yandex.lookart.R;
import com.shagi.yandex.lookart.adapter.ListArtistAdapter;


/**
 * Инициализирует список исполнитлей
 */
public class RecyclerArtistFragment extends Fragment {

    OnArtistSelectedListener onArtistSelectedListener;

    /**
     * Слушатель выобра исполнителя из списка
     */
    public interface OnArtistSelectedListener {
        /**
         * Слушает нажатие пунтка списка
         *
         * @param position    позиция исполнителя в списке
         * @param openInfoTab значение определяет переход на другую табу
         */
        void onArtistSelected(int position, boolean openInfoTab);
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

        final ListArtistAdapter artistAdapter = new ListArtistAdapter(((MainActivity) getActivity()).getArtists());

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

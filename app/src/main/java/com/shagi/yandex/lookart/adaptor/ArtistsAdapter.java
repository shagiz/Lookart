package com.shagi.yandex.lookart.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shagi.yandex.lookart.DownloadImageTask;
import com.shagi.yandex.lookart.R;
import com.shagi.yandex.lookart.pojo.Artist;

import java.util.List;

/**
 * Created by Shagi on 06.04.2016.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    List<Artist> artists;

    public ArtistsAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public ArtistsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_artist, parent, false);

        ImageView smallCover = (ImageView) v.findViewById(R.id.small_cover);
        TextView name = (TextView) v.findViewById(R.id.tvArtistName);
        TextView style = (TextView) v.findViewById(R.id.tvStyle);
        TextView albums = (TextView) v.findViewById(R.id.tvAlbums);

        return new ViewHolder(v, smallCover, name, style, albums);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Artist artist = artists.get(position);
        new DownloadImageTask(holder.smallCover).execute(artist.getCover().getSmall());
        holder.artistName.setText(artist.getName());
        String style = "";
        for (String string : artist.getGenres()) {
            style += string + " ";
        }
        holder.artistStyle.setText(style);
        String albums = "альбомов " + artist.getAlbums() + ", треков " + artist.getTracks();
        holder.artistAlbums.setText(albums);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView smallCover;
        public TextView artistName;
        public TextView artistStyle;
        public TextView artistAlbums;

        public ViewHolder(View v, ImageView img, TextView artistName, TextView artistStyle, TextView artistAlbums) {
            super(v);
            smallCover = img;
            this.artistName = artistName;
            this.artistStyle = artistStyle;
            this.artistAlbums = artistAlbums;
        }
    }
}

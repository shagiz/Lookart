package com.shagi.yandex.lookart.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shagi.yandex.lookart.R;
import com.shagi.yandex.lookart.pojo.Artist;


import java.util.List;

/**
 * Получает элемент пункта списка из модели artist_list_model.xml
 * Заполняет элемент данными
 */
public class ListArtistAdapter extends RecyclerView.Adapter<ListArtistAdapter.ViewHolder> {

    List<Artist> artists;
    OnItemClickListener listener;

    public ListArtistAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public ListArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_model, parent, false);

        ImageView smallCover = (ImageView) v.findViewById(R.id.small_cover);
        TextView name = (TextView) v.findViewById(R.id.tvArtistName);
        TextView style = (TextView) v.findViewById(R.id.tvListStyle);
        TextView albums = (TextView) v.findViewById(R.id.tvListAlbums);

        return new ViewHolder(v, smallCover, name, style, albums);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = artists.get(position);

        ImageLoader.getInstance().displayImage(artist.getCover().getSmall(), holder.smallCover);

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

    /**
     * В этом классе каждому элементу списка устанавливаем слушатели на касание
     * и добавляем анимацию перехода на другую вкладку.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(final View view) {

            if (listener != null) {
                ObjectAnimator flipIn = ObjectAnimator.ofFloat(smallCover, "rotationY", -180f, 0f);
                flipIn.setDuration(750);
                flipIn.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                "translationX", 0f, itemView.getWidth());

                        ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView,
                                "translationX", itemView.getWidth(), 0f);

                        translationX.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                listener.onItemClick(view, getAdapterPosition());
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

                        AnimatorSet translaitonSet = new AnimatorSet();
                        translaitonSet.play(translationX).before(translationXBack);
                        translaitonSet.start();

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                flipIn.start();
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener listener) {
        this.listener = listener;
    }

}

package com.shagi.yandex.lookart.pojo;

/**
 * Created by Shagi on 06.04.2016.
 */
public class Artist {
    private String id;

    private Cover cover;

    private String[] genres;

    private String description;

    private String link;

    private String albums;

    private String name;

    private String tracks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", cover = " + cover + ", genres = " + genres + ", description = " + description + ", link = " + link + ", albums = " + albums + ", name = " + name + ", tracks = " + tracks + "]";
    }
}

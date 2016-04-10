package com.shagi.yandex.lookart.pojo;

import java.io.Serializable;
import java.util.Arrays;

/**
 * POJO
 * @author Shagi
 */
public class Artist implements Serializable{
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
        return "ClassPojo [id = " + id + ", cover = " + cover + ", genres = " + Arrays.toString(genres) + ", description = " + description + ", link = " + link + ", albums = " + albums + ", name = " + name + ", tracks = " + tracks + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != null ? !id.equals(artist.id) : artist.id != null) return false;
        if (cover != null ? !cover.equals(artist.cover) : artist.cover != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(genres, artist.genres) && (description != null ? description.equals(artist.description) : artist.description == null && (link != null ? link.equals(artist.link) : artist.link == null && (albums != null ? albums.equals(artist.albums) : artist.albums == null && (name != null ? name.equals(artist.name) : artist.name == null && (tracks != null ? tracks.equals(artist.tracks) : artist.tracks == null)))));

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(genres);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }
}

package org.classes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Library {
    public List<Album> albums;
    public List<Playlist> playlists;

    public Library() {
        this.albums = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }
    public Library(List<Album> albums, List<Playlist> playlists) {
        this.albums = albums;
        this.playlists = playlists;
    }

    public void printAlbums() {
        System.out.println("The library contains the following albums:");
        int i = 1;
        for(Album a : albums) {
            System.out.println(i + ": " + a.md.name + " (" + a.getNumberOfTracks() + " tracks)");
            i++;
        }
    }
    public void printPlaylists() {
        System.out.println("The library contains the following playlists:");
        int i = 1;
        for(Playlist p : playlists) {
            System.out.println(i + ": " + p.md.name + " (" + p.getNumberOfTracks() + " tracks)");
            i++;
        }
    }
}

package org.example;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends Album{
    public Playlist(Metadata md, User creator, List<Song> songList) {
        super(md, creator, songList);
    }
    public Playlist() {
        this.md = new Metadata();
        this.creator = new User();
        this.songList = new ArrayList<>();
    }

    public void play() { System.out.println("Playlist: play!\n"); }
    public void pause() { System.out.println("Playlist: pause!\n"); }
    public void stop() { System.out.println("Playlist: stop!\n"); }
}

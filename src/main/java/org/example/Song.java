package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Song implements Playable, Downloadable, Comparable<Song> {
    Metadata songMD;

    public Song(Metadata songMD) {
        this.songMD = songMD;
    }
    public Song() {
        this.songMD = new Metadata();
    }

    public String getName() { return this.songMD.name; }

    void printMetadata() {
        System.out.println("Type: " + getClass().getName());
        System.out.println("Name: " + songMD.name);
        System.out.println("Length: " + songMD.formattedLength(songMD.length));
        System.out.println();
    }
    public void play() {
        System.out.println("Song played!\n");
    }
    public void pause() {
        System.out.println("Song paused!\n");
    }
    public void stop() {
        System.out.println("Song stopped!\n");
    }
    public void download() {
        System.out.println("Song downloaded!\n");
    }

    public int compareTo(Song a) {
        if(this.songMD.length > a.songMD.length) return 1;
        else if(this.songMD.length < a.songMD.length) return -1;
        else {
            return Integer.compare(this.songMD.name.compareTo(a.songMD.name), 0);
        }
    }
}

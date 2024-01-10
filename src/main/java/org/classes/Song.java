package org.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.interfaces.Downloadable;
import org.interfaces.Playable;

@Getter
@Setter
public class Song implements Playable, Downloadable, Comparable<Song> {
    public Metadata songMD;

    public Song(Metadata songMD) {
        this.songMD = songMD;
    }
    public Song() {
        this.songMD = new Metadata();
    }
    @JsonIgnore
    public String getName() { return this.songMD.name; }

    public void printMetadata() {
        System.out.println("Name: " + songMD.name);
        System.out.println("Length: " + songMD.formattedLength(songMD.length));
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

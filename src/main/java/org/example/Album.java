package org.example;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Album implements Playable, Comparable<Album>{
    Metadata md;
    List<Song> songList;
    User creator;

    public Album() {
        this.md = new Metadata();
        this.songList = new ArrayList<>();
        this.creator = new User();
    }

    public Album(Metadata md, User creator, List<Song> songList) {
        this.md = md;
        this.songList = songList;
        this.creator = creator;
        computeTotalLength();
    }

    int getNumberOfTracks() { return songList.size(); }
    public String getName() { return this.md.name; }

    void printTracks() {
        System.out.println(this.md.name + " has the following tracks:");
        int i = 1;
        for(Song s : songList) {
            System.out.println(i + ": " + s.songMD.name + " (" + s.songMD.formattedLength(s.songMD.length) + ")");
            i++;
        }
    }

    void computeTotalLength() {
        int minutes = 0, seconds = 0;
        for(Song s : this.songList) { // Compute total amount of minutes and seconds separately
            minutes += (int)s.songMD.length;
            seconds += (int) (s.songMD.length * 100 % 100);
        }
        if(seconds > 60)  { // Transform seconds in minutes and seconds
            minutes += seconds / 60;
            this.md.length = minutes + (double) (seconds % 60) / 100;
        }
        else this.md.length = minutes + ((double) seconds / 100);
    }

    void printMetadata() {
        System.out.println("Type: " + getClass().getName());
        System.out.println("Name: " + md.name);
        System.out.println("Creator: " + creator.userMD.name);
        System.out.println("Date of creation: " + md.dateOfCreation);
        System.out.println("Length: " + md.formattedLength(md.length));
        System.out.println("Description: " + md.description);
        System.out.println();
    }

    public void play() { System.out.println("Album: play!\n"); }
    public void pause() { System.out.println("Album: pause!\n"); }
    public void stop() { System.out.println("Album: stop!\n"); }

    public int compareTo(Album a) {
        if(this.getNumberOfTracks() > a.getNumberOfTracks()) return 1;
        else if(this.getNumberOfTracks() < a.getNumberOfTracks()) return -1;
        else {
            return Integer.compare(this.md.name.compareTo(a.md.name), 0);
        }
    }
}

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
        computeTotalLength();
    }

    public Album(Metadata md, User creator, List<Song> songList) {
        this.md = md;
        this.songList = songList;
        this.creator = creator;
        computeTotalLength();
    }

    int getNumberOfTracks() { return songList.size(); }
    public String getName() { return this.md.getName(); }

    void printTracks() {
        System.out.println(this.md.name + " has the following tracks:");
        int i = 1;
        for(Song s : songList) {
            System.out.println(i + ": " + s.songMD.getName() + " (" + s.songMD.formattedLength(s.songMD.getLength()) + ")");
            i++;
        }
    }

    void computeTotalLength() {
        int minutes = 0, seconds = 0;
        for(Song s : this.songList) { // Compute total amount of minutes and seconds separately
            minutes += (int)s.songMD.getLength();
            seconds += (int) (s.songMD.getLength() * 100 % 100);
        }
        if(seconds > 60)  { // Transform seconds in minutes and seconds
            minutes += seconds / 60;
            this.md.length = minutes + (double) (seconds % 60) / 100;
        }
        else this.md.length = minutes + ((double) seconds / 100);
    }

    void addTrack(Song s) {
        this.songList.add(s);
    }

    void printMetadata() {
        System.out.println("Name: " + getName());
        System.out.println("Creator: " + creator.getUserMD().getName());
        System.out.println("Date of creation: " + md.getDateOfCreation());
        System.out.println("Length: " + md.formattedLength(md.getLength()));
        System.out.println("Description: " + md.getDescription());
        System.out.println();
    }

    public void play() { System.out.println("Album: play!\n"); }
    public void pause() { System.out.println("Album: pause!\n"); }
    public void stop() { System.out.println("Album: stop!\n"); }

    public int compareTo(Album a) {
        if(this.getNumberOfTracks() > a.getNumberOfTracks()) return 1;
        else if(this.getNumberOfTracks() < a.getNumberOfTracks()) return -1;
        else {
            return Integer.compare(this.md.getName().compareTo(a.md.getName()), 0);
        }
    }
}

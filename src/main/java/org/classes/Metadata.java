package org.classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metadata {
    public String name;
    public String dateOfCreation;
    public String description;
    public double length;

    //Album or Playlist
    public Metadata(String name, String dateOfCreation, String description) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.description = description;
    }
    //Artist
    public Metadata(String name, String description) {
        this.name = name;
        this.description = description;
    }
    //Song
    public Metadata(String name, double length) {
        this.name = name;
        this.length = length;
    }
    public Metadata() {
        this.name = null;
        this.dateOfCreation = null;
        this.description = null;
        this.length = 0;
    }
    public String formattedLength(double length) {
        int minutes = (int)length;
        int seconds = (int) (length * 100 % 100);

        String stringSeconds = String.valueOf(seconds);
        if(seconds < 10) stringSeconds = "0" + seconds;
        return minutes + ":" + stringSeconds;
    }
}

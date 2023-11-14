package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metadata {
    String name;
    String dateOfCreation;
    String description;
    double length;

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
        this.name = "";
        this.dateOfCreation = "";
        this.description = "";
        this.length = 0;
    }
    public String formattedLength(double length) {
        int minutes = (int)length;
        int seconds = (int) (length * 100 % 100);

        return minutes + ":" + seconds;
    }
}

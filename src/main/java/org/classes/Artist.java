package org.classes;

import org.application.InputDevice;
import org.interfaces.Downloadable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Artist extends User implements Downloadable {
    public Artist(Metadata userMD, Credentials credentials) {
        super(userMD, credentials);
    }
    public Artist() {
        this.credentials = new Credentials();
        this.userMD = new Metadata();
    }

    public void download() {
        System.out.println("Artist metadata downloaded!\n");
    }

    public Album createAlbum(InputDevice id) {
        Album newAlbum = new Album();
        Metadata newMetadata = new Metadata();
        System.out.print("Name of album: ");
        newMetadata.setName(id.read());
        System.out.print("Description of album: ");
        newMetadata.setDescription(id.read());

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        newMetadata.setDateOfCreation(formattedDate);

        newAlbum.setMd(newMetadata);
        newAlbum.setCreator(this);

        return newAlbum;
    }
    public Song createSong(InputDevice id) {
        Song newSong = new Song();
        Metadata newMetadata = new Metadata();
        System.out.print("Name of song: ");
        newMetadata.setName(id.read());
        System.out.print("Length of song: ");
        newMetadata.setLength(id.readFloat());
        newSong.setSongMD(newMetadata);

        return newSong;
    }
}

package org.classes;

import org.interfaces.Downloadable;

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
}

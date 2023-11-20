package org.classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public Metadata userMD;
    public Credentials credentials;

    public User(Metadata userMD, Credentials credentials) {
        this.credentials = credentials;
        this.userMD = userMD;
    }

    public User() {
        this.userMD = new Metadata();
        this.credentials = new Credentials();
    }

    public void printMetadata() {
        System.out.println("Name: " + userMD.name);
        System.out.println("Description: " + userMD.description);
        System.out.println();
    }
}

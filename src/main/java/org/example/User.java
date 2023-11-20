package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    Metadata userMD;
    Credentials credentials;

    public User(Metadata userMD, Credentials credentials) {
        this.credentials = credentials;
        this.userMD = userMD;
    }

    public User() {
        this.userMD = new Metadata();
        this.credentials = new Credentials();
    }

    void printMetadata() {
        System.out.println("Name: " + userMD.name);
        System.out.println("Description: " + userMD.description);
        System.out.println();
    }
}

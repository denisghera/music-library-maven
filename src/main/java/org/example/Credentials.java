package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credentials {
    String username;
    String password;

    public Credentials() {
        this.username = "";
        this.password = "";
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

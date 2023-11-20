package org.classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credentials {
    public String username;
    public String password;

    public Credentials() {
        this.username = "";
        this.password = "";
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

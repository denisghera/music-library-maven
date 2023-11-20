package org.classes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserDatabase {
    public List<User> userList;

    public UserDatabase() { this.userList = new ArrayList<>(); }
    public UserDatabase(List<User> userList) {
        this.userList = userList;
    }

    public void printContent() {
        System.out.println("The following users' credentials were found:");
        int i = 1;
        for(User u : userList) {
            System.out.println("username: " + u.credentials.username + "\npassword: " + u.credentials.password + "\n");
            i++;
        }
    }
}

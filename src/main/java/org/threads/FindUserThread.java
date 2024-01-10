package org.threads;

import lombok.Getter;
import org.classes.Credentials;
import org.classes.User;

import java.util.List;

public class FindUserThread extends Thread {
    private List<User> userList;
    private String inputUsername;
    private String inputPassword;
    private volatile boolean userFound = false;
    @Getter
    private User resultUser;

    public FindUserThread(List<User> userList, String inputUsername, String inputPassword) {
        this.userList = userList;
        this.inputUsername = inputUsername;
        this.inputPassword = inputPassword;
    }

    @Override
    public void run() {
        for (User user : userList) {
            // Check if user is found by another thread
            if (userFound) {
                return;
            }
            Credentials userCredentials = user.credentials;
            if (userCredentials.username.equals(inputUsername) && userCredentials.password.equals(inputPassword)) {
                resultUser = user;
                userFound = true;
                return;
            }
        }
    }
}

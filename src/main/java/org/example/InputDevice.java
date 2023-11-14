package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

public class InputDevice{
    InputStream is;
    Scanner scan;
    public InputDevice(InputStream is) {
        this.is = is;
        scan = new Scanner(is);
    }

    public String read() {
        try {
            if (scan.hasNextLine()) {
                return scan.nextLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Library fetchLibrary(String folderName) {
        Library library = new Library();
        try {
            File libraryFile = new File(folderName + "Library.json");
            ObjectMapper objectMapper = new ObjectMapper();
            library = objectMapper.readValue(libraryFile, Library.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return library;
    }

    public UserDatabase fetchUserDatabase(String folderName) {
        UserDatabase userDatabase = new UserDatabase();
        try {
            File userbaseFile = new File(folderName + "UserDatabase.json");
            ObjectMapper objectMapper = new ObjectMapper();
            userDatabase = objectMapper.readValue(userbaseFile, UserDatabase.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDatabase;
    }

}

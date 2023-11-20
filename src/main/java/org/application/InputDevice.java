package org.application;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.classes.Library;
import org.classes.UserDatabase;

import java.io.*;
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
            System.out.println("Problem reading user's input!");
        }
        return null;
    }
    public Library fetchLibrary(String folderName) {
        Library library = new Library();
        try {
            File libraryFile = new File(folderName + "Library.json");
            ObjectMapper objectMapper = new ObjectMapper();
            library = objectMapper.readValue(libraryFile, Library.class);
        } catch (FileNotFoundException e) {
            System.out.println("Library file not found!");
        } catch (IOException e) {
            System.out.println("Problem reading from library file!");
        } catch (Exception e) {
            System.out.println("An error occurred while fetching library information!");
        }
        return library;
    }
    public UserDatabase fetchUserDatabase(String folderName) {
        UserDatabase userDatabase = new UserDatabase();
        try {
            File userbaseFile = new File(folderName + "UserDatabase.json");
            ObjectMapper objectMapper = new ObjectMapper();
            userDatabase = objectMapper.readValue(userbaseFile, UserDatabase.class);
        } catch (FileNotFoundException e) {
            System.out.println("UserDatabase file not found!");
        } catch (IOException e) {
            System.out.println("Problem reading from user database file!");
        } catch (Exception e) {
            System.out.println("An error occurred while fetching user database!");
        }
        return userDatabase;
    }
}

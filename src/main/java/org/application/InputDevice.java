package org.application;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.classes.Album;
import org.classes.Library;
import org.classes.Playlist;
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
            System.err.println("Problem reading user's input!");
        }
        return null;
    }
    public int readInt() {
        while(true) {
            String input = scan.nextLine();
            if(isInt(input)) {
                return Integer.parseInt(input);
            } else {
                System.err.print("Please enter a valid integer number: ");
            }
        }
    }
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public float readFloat() {
        while (true) {
            String input = scan.nextLine();
            if (isFloat(input)) {
                return Float.parseFloat(input);
            } else {
                System.err.print("Please enter a valid float number: ");
            }
        }
    }
    private static boolean isFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public Library fetchLibrary(String folderName) {
        Library library = new Library();
        try {
            File libraryFile = new File(folderName + "Library.json");
            ObjectMapper objectMapper = new ObjectMapper();
            library = objectMapper.readValue(libraryFile, Library.class);
        } catch (FileNotFoundException e) {
            System.err.println("Library file not found!");
        } catch (IOException e) {
            System.err.println("Problem reading from library file!");
        } catch (Exception e) {
            System.err.println("An error occurred while fetching library information!");
        }
        for(Album a : library.albums) a.computeTotalLength();
        for(Playlist p : library.playlists) p.computeTotalLength();
        return library;
    }
    public UserDatabase fetchUserDatabase(String folderName) {
        UserDatabase userDatabase = new UserDatabase();
        try {
            File userbaseFile = new File(folderName + "UserDatabase.json");
            ObjectMapper objectMapper = new ObjectMapper();
            userDatabase = objectMapper.readValue(userbaseFile, UserDatabase.class);
        } catch (FileNotFoundException e) {
            System.err.println("UserDatabase file not found!");
        } catch (IOException e) {
            System.err.println("Problem reading from user database file!");
        } catch (Exception e) {
            System.err.println("An error occurred while fetching user database!");
        }
        return userDatabase;
    }
}

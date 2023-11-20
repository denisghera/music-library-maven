package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

public class OutputDevice {
    OutputStream os;
    public OutputDevice(OutputStream os) { this.os = os; }

    public void print(String data) {
        try {
            PrintStream printStream = new PrintStream(os);
            printStream.println(data);
        } catch(Exception e) {
            System.out.println("Problem occurred while printing!");
        }
    }

    public void saveLibrary(String folderName, Library library) {
        try {
            File libraryFile = new File(folderName + "Library.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            objectMapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
            objectMapper.writeValue(libraryFile, library);
        } catch(FileNotFoundException e) {
            System.out.println("Library file not found!");
        } catch (IOException e) {
            System.out.println("Problem writing to library file!");
        } catch (Exception e) {
            System.out.println("An error occurred while saving the library information!");
        }
    }

    public void saveUserDatabase(String folderName, UserDatabase userDatabase) {
        try {
            File userbaseFile = new File(folderName + "UserDatabase.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            objectMapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
            objectMapper.writeValue(userbaseFile, userDatabase);
        } catch(FileNotFoundException e) {
            System.out.println("UserDatabase file not found!");
        } catch (IOException e) {
            System.out.println("Problem writing to user database file!");
        } catch (Exception e) {
            System.out.println("An error occurred while saving the user database!");
        }
    }
}

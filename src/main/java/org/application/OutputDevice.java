package org.application;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.classes.Library;
import org.classes.UserDatabase;

import java.io.*;

public class OutputDevice {
    OutputStream os;
    public OutputDevice(OutputStream os) { this.os = os; }

    public void print(String data) {
        try {
            PrintStream printStream = new PrintStream(os);
            printStream.println(data);
        } catch(Exception e) {
            System.err.println("Problem occurred while printing!");
        }
    }

    public void saveLibrary(String folderName, Library library) {
        try {
            File libraryFile = new File(folderName + "Library.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            objectMapper.writeValue(libraryFile, library);
        } catch(FileNotFoundException e) {
            System.err.println("Library file not found!");
        } catch (IOException e) {
            System.err.println("Problem writing to library file!");
        } catch (Exception e) {
            System.err.println("An error occurred while saving the library information!");
        }
    }

    public void saveUserDatabase(String folderName, UserDatabase userDatabase) {
        try {
            File userbaseFile = new File(folderName + "UserDatabase.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            objectMapper.writeValue(userbaseFile, userDatabase);
        } catch(FileNotFoundException e) {
            System.err.println("UserDatabase file not found!");
        } catch (IOException e) {
            System.err.println("Problem writing to user database file!");
        } catch (Exception e) {
            System.err.println("An error occurred while saving the user database!");
        }
    }
}

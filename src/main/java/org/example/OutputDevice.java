package org.example;
import java.io.OutputStream;
import java.io.PrintStream;

public class OutputDevice {
    OutputStream os;
    public OutputDevice(OutputStream os) { this.os = os; }

    public void print(String data) {
        try {
            PrintStream printStream = new PrintStream(os);
            printStream.println(data);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData(Library l, UserDatabase ud, String folderName) {
        //to be implemented
    }
}

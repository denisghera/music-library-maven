package org.application;

import org.threads.MultiThreadedServer;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("server")) {
            MultiThreadedServer server = new MultiThreadedServer(8080);
            server.startServer();
        } else if (args.length == 3 && args[0].equals("start")) {
            String username = args[1];
            String password = args[2];

            try {
                Socket socket = new Socket("localhost", 8080);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(username);

                String serverResponse = in.readLine();
                System.out.println("Server says: " + serverResponse);

                InputDevice dIN = new InputDevice(System.in);
                OutputDevice dOUT = new OutputDevice(System.out);
                Application app = new Application(dIN, dOUT);
                app.startApplication(username, password);

                out.close();
                in.close();
                socket.close();
            } catch (ConnectException connectionRefused) {
                System.err.println("Connection refused. Please make sure the server is running.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Invalid arguments. To start the server: java Main server");
            System.err.println("To connect to the server: java Main start <username> <password>");
        }
    }
}

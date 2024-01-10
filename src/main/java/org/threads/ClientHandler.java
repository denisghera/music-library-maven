package org.threads;

import java.io.*;
import java.net.*;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private MultiThreadedServer server;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket, MultiThreadedServer server) {
        this.clientSocket = socket;
        this.server = server;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void close() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.println("Connection successful.");
            username = in.readLine();
            System.out.println("User " + username + " connected.");

            // Notify other clients about the new connection
            server.broadcastMessage(username + " connected.", this);

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                server.broadcastMessage(username + ": " + clientMessage, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
            server.broadcastMessage(username + " disconnected.", this);
            System.out.println("User " + username + " disconnected.");
        }
    }
}

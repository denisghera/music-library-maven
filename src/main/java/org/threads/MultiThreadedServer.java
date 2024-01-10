package org.threads;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadedServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    public MultiThreadedServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        System.out.println("Server started.");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(String message, ClientHandler excludeClient) {
        for (ClientHandler client : clients) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }
}

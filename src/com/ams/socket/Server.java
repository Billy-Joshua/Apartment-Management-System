package com.ams.socket;

import java.io.*;
import java.net.*;

/**
 * Server - Handles multiple client connections
 * Accepts connections and creates a thread for each client
 */
public class Server {
    
    private ServerSocket serverSocket;
    private int port;
    private static int clientCount = 0;
    
    public Server(int port) {
        this.port = port;
    }
    
    /**
     * Start the server and listen for connections
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("[SERVER] Started on port " + port);
            System.out.println("[SERVER] Waiting for connections...\n");
            
            // Accept connections indefinitely
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                
                System.out.println("[SERVER] Client #" + clientCount + " connected from " + 
                    clientSocket.getInetAddress().getHostAddress());
                
                // Create a new thread for each client
                Thread clientThread = new Thread(new ClientHandler(clientSocket, clientCount));
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("[SERVER] Error starting server: " + e.getMessage());
        } finally {
            stop();
        }
    }
    
    /**
     * Stop the server
     */
    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("[SERVER] Stopped");
            }
        } catch (IOException e) {
            System.err.println("[SERVER] Error stopping server: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        int port = 5555;
        Server server = new Server(port);
        server.start();
    }
}

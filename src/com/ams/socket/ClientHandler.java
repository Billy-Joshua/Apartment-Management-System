package com.ams.socket;

import java.io.*;
import java.net.*;

/**
 * ClientHandler - Handles individual client requests
 * Runs in a separate thread for each connected client
 */
public class ClientHandler implements Runnable {
    
    private Socket socket;
    private int clientId;
    private BufferedReader reader;
    private PrintWriter writer;
    
    public ClientHandler(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }
    
    @Override
    public void run() {
        try {
            // Initialize streams
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            // Send welcome message
            writer.println("WELCOME:Client #" + clientId + " connected to Apartment Management System");
            System.out.println("[CLIENT #" + clientId + "] Connection established");
            
            // Handle client requests
            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println("[CLIENT #" + clientId + "] Request: " + request);
                
                String response = processRequest(request);
                writer.println(response);
                System.out.println("[CLIENT #" + clientId + "] Response sent: " + response);
            }
            
        } catch (IOException e) {
            System.err.println("[CLIENT #" + clientId + "] Error: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }
    
    /**
     * Process client requests
     */
    private String processRequest(String request) {
        String[] parts = request.split(":");
        String command = parts[0];
        
        switch (command) {
            case "GET_TENANTS":
                return "TENANTS:Data for all tenants";
            
            case "GET_ROOMS":
                return "ROOMS:Data for all rooms";
            
            case "GET_PAYMENTS":
                return "PAYMENTS:Data for all payments";
            
            case "GET_CONTRACTS":
                return "CONTRACTS:Data for all contracts";
            
            case "ADD_TENANT":
                return "ADD_TENANT_SUCCESS:Tenant added successfully";
            
            case "UPDATE_PAYMENT":
                return "UPDATE_PAYMENT_SUCCESS:Payment updated";
            
            case "PING":
                return "PONG:Server is alive";
            
            default:
                return "ERROR:Unknown command";
        }
    }
    
    /**
     * Close connection
     */
    private void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("[CLIENT #" + clientId + "] Disconnected");
            }
        } catch (IOException e) {
            System.err.println("[CLIENT #" + clientId + "] Error closing connection: " + e.getMessage());
        }
    }
}

package com.ams.config;

import com.ams.utils.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConfig class - Manages database connections
 * Provides singleton pattern for database connection management
 */
public class DatabaseConfig {
    
    private static Connection connection = null;
    
    /**
     * Establishes database connection
     * @return Connection object if successful, null otherwise
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL Driver
                Class.forName(Constants.DB_DRIVER);
                
                // Establish connection
                connection = DriverManager.getConnection(
                    Constants.DB_URL,
                    Constants.DB_USER,
                    Constants.DB_PASSWORD
                );
                
                System.out.println("[DATABASE] Connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[DATABASE] MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("[DATABASE] Connection failed!");
            e.printStackTrace();
        }
        
        return connection;
    }
    
    /**
     * Closes database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DATABASE] Connection closed!");
            }
        } catch (SQLException e) {
            System.err.println("[DATABASE] Error closing connection!");
            e.printStackTrace();
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("[DATABASE] Connection test: SUCCESS");
            return true;
        } else {
            System.out.println("[DATABASE] Connection test: FAILED");
            return false;
        }
    }
}

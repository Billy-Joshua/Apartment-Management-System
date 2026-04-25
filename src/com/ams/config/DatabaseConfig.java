package com.ams.config;

import com.ams.utils.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConfig class - Manages database connections
 * Provides thread-safe singleton pattern for database connection management
 */
public class DatabaseConfig {
    
    private static volatile Connection connection = null;
    private static final Object lock = new Object();
    
    /**
     * Establishes database connection with thread safety
     * @return Connection object if successful, null otherwise
     */
    public static Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            synchronized (lock) {
                if (connection == null || isConnectionClosed()) {
                    connection = createConnection();
                }
            }
        }
        return connection;
    }
    
    /**
     * Check if connection is closed or invalid
     */
    private static boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }
    
    /**
     * Create new database connection
     */
    private static Connection createConnection() {
        try {
            // Load MySQL Driver
            Class.forName(Constants.DB_DRIVER);
            
            // Establish connection
            Connection conn = DriverManager.getConnection(
                Constants.DB_URL,
                Constants.DB_USER,
                Constants.DB_PASSWORD
            );
            
            System.out.println("[DATABASE] Connected successfully!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("[DATABASE] MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("[DATABASE] Connection failed!");
            e.printStackTrace();
        }
        return null;
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

package com.ams.config;

import com.ams.utils.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ConnectionPool - Thread-safe database connection pool
 * Manages a pool of database connections for better performance and resource management
 */
public class ConnectionPool {

    private static final int POOL_SIZE = 10;
    private static final int TIMEOUT_MS = 5000; // 5 seconds

    private static BlockingQueue<Connection> connectionPool;
    private static ConnectionPool instance;

    private ConnectionPool() throws SQLException {
        initializePool();
    }

    /**
     * Get singleton instance
     */
    public static synchronized ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * Initialize connection pool
     */
    private void initializePool() throws SQLException {
        connectionPool = new ArrayBlockingQueue<>(POOL_SIZE);

        // Load MySQL Driver
        try {
            Class.forName(Constants.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }

        // Create initial connections
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection conn = createConnection();
            if (conn != null) {
                connectionPool.offer(conn);
            }
        }

        if (connectionPool.isEmpty()) {
            throw new SQLException("Failed to create database connections");
        }

        System.out.println("[CONNECTION_POOL] Initialized with " + connectionPool.size() + " connections");
    }

    /**
     * Create new database connection
     */
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
            Constants.DB_URL,
            Constants.DB_USER,
            Constants.DB_PASSWORD
        );
    }

    /**
     * Get connection from pool
     */
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = connectionPool.poll(TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS);
            if (conn == null || conn.isClosed()) {
                // Create new connection if pool is empty or connection is closed
                conn = createConnection();
            }
            return conn;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for connection", e);
        }
    }

    /**
     * Return connection to pool
     */
    public void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed() && connectionPool.size() < POOL_SIZE) {
                    // Reset connection state
                    conn.setAutoCommit(true);
                    connectionPool.offer(conn);
                } else {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("[CONNECTION_POOL] Error releasing connection: " + e.getMessage());
                try {
                    conn.close();
                } catch (SQLException ex) {
                    // Ignore
                }
            }
        }
    }

    /**
     * Close all connections
     */
    public void shutdown() {
        Connection conn;
        while ((conn = connectionPool.poll()) != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("[CONNECTION_POOL] Error closing connection: " + e.getMessage());
            }
        }
        System.out.println("[CONNECTION_POOL] Shutdown complete");
    }

    /**
     * Get pool status
     */
    public String getStatus() {
        return String.format("Pool size: %d/%d", connectionPool.size(), POOL_SIZE);
    }
}
package com.ams.controller;

import com.ams.config.ConnectionPool;
import com.ams.model.User;
import com.ams.utils.SecurityUtils;
import java.sql.*;

/**
 * AuthController - Handles user authentication and authorization
 * Implements secure password hashing and connection pooling
 */
public class AuthController {

    private ConnectionPool connectionPool;

    public AuthController() {
        try {
            this.connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }

    /**
     * Authenticate user with username and password
     */
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (storedHash != null && SecurityUtils.verifyPassword(password, storedHash)) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"), // Don't return actual hash
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return null;
    }

    /**
     * Register new user with secure password hashing
     */
    public boolean register(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }

        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            // Check if username already exists
            if (usernameExists(user.getUsername(), conn)) {
                return false;
            }

            String hashedPassword = SecurityUtils.hashPassword(user.getPassword());

            String query = "INSERT INTO users (username, password, email, role, status, created_date) VALUES (?, ?, ?, ?, ?, NOW())";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername().trim());
            ps.setString(2, hashedPassword);
            ps.setString(3, user.getEmail() != null ? user.getEmail().trim() : null);
            ps.setString(4, user.getRole() != null ? user.getRole() : "TENANT");
            ps.setString(5, "ACTIVE");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }
    
    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            return usernameExists(username, conn);
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }

    /**
     * Check if username exists (with provided connection)
     */
    private boolean usernameExists(String username, Connection conn) throws SQLException {
        String query = "SELECT user_id FROM users WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username.trim());

        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(int userId) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"), // Don't return actual hash
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return null;
    }
}

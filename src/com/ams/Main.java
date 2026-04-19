package com.ams;

import com.ams.config.DatabaseConfig;
import com.ams.view.LoginFrame;
import javax.swing.*;

/**
 * Main - Entry point of the Apartment Management System
 * Initializes database connection and starts the application
 */
public class Main {
    
    public static void main(String[] args) {
        // Test database connection
        System.out.println("=".repeat(50));
        System.out.println("APARTMENT MANAGEMENT SYSTEM - INITIALIZATION");
        System.out.println("=".repeat(50));
        
        System.out.println("\n[INIT] Testing database connection...");
        if (!DatabaseConfig.testConnection()) {
            System.err.println("[ERROR] Failed to connect to database!");
            System.err.println("[ERROR] Please ensure:");
            System.err.println("  1. XAMPP MySQL is running");
            System.err.println("  2. Database 'apartment_management_system' exists");
            System.err.println("  3. Database tables are created (run database_schema.sql)");
            System.err.println("  4. Credentials in Constants.java are correct");
            
            JOptionPane.showMessageDialog(null, 
                "Database Connection Failed!\n\n" +
                "Ensure XAMPP MySQL is running and database is created.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        System.out.println("[INIT] Database connection successful!\n");
        
        // Launch GUI
        System.out.println("[INIT] Launching application...\n");
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
        
        System.out.println("[OK] Application started successfully!");
        System.out.println("=".repeat(50));
    }
}

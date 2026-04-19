package com.ams.utils;

/**
 * Constants class - Contains all application-wide constants
 * Used to maintain consistency across the application
 */
public class Constants {
    
    // Application Info
    public static final String APP_TITLE = "Apartment Management System";
    public static final String APP_VERSION = "1.0.0";
    
    // Database Configuration
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/apartment_management_system?useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";
    
    // User Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_TENANT = "TENANT";
    
    // Room Status
    public static final String ROOM_VACANT = "VACANT";
    public static final String ROOM_OCCUPIED = "OCCUPIED";
    public static final String ROOM_MAINTENANCE = "MAINTENANCE";
    
    // Payment Status
    public static final String PAYMENT_PAID = "PAID";
    public static final String PAYMENT_PENDING = "PENDING";
    public static final String PAYMENT_OVERDUE = "OVERDUE";
    
    // Contract Status
    public static final String CONTRACT_ACTIVE = "ACTIVE";
    public static final String CONTRACT_EXPIRED = "EXPIRED";
    public static final String CONTRACT_TERMINATED = "TERMINATED";
    
    // Window Dimensions
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 650;
    
    // Socket Configuration
    public static final int SOCKET_PORT = 5555;
    public static final String SOCKET_HOST = "localhost";
    
    // Validation Messages
    public static final String ERROR_EMPTY_FIELD = "Please fill all required fields!";
    public static final String ERROR_INVALID_EMAIL = "Invalid email format!";
    public static final String ERROR_INVALID_PHONE = "Phone must be 10 digits!";
    public static final String ERROR_INVALID_DATE = "Invalid date format!";
    public static final String SUCCESS_OPERATION = "Operation completed successfully!";
}

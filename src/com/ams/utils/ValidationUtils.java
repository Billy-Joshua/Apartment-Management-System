package com.ams.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * ValidationUtils class - Provides validation methods for input data
 * Used to validate user input before database operations
 */
public class ValidationUtils {
    
    private static final String EMAIL_PATTERN = 
        "^[A-Za-z0-9+_.-]+@(.+)$";
    
    private static final String PHONE_PATTERN = 
        "^[0-9]{10}$";
    
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    
    /**
     * Validates if string is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return emailPattern.matcher(email).matches();
    }
    
    /**
     * Validates phone number format (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        return phonePattern.matcher(phone).matches();
    }
    
    /**
     * Validates if amount is positive number
     */
    public static boolean isValidAmount(String amount) {
        try {
            double value = Double.parseDouble(amount);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates if number is integer
     */
    public static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates date format (yyyy-MM-dd)
     */
    public static boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Validates if password is strong
     * Requirements: At least 6 characters
     */
    public static boolean isValidPassword(String password) {
        return !isEmpty(password) && password.length() >= 6;
    }
    
    /**
     * Validates username format
     * Requirements: 4-20 alphanumeric characters
     */
    public static boolean isValidUsername(String username) {
        return !isEmpty(username) && 
               username.length() >= 4 && 
               username.length() <= 20 && 
               username.matches("^[a-zA-Z0-9_]+$");
    }
    
    /**
     * Validates if end date is after start date
     */
    public static boolean isEndDateAfterStartDate(Date startDate, Date endDate) {
        return endDate != null && startDate != null && endDate.after(startDate);
    }
}

package com.ams.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils class - Provides date utility methods
 * Used for date formatting, calculations, and conversions
 */
public class DateUtils {
    
    private static final SimpleDateFormat DATE_FORMAT = 
        new SimpleDateFormat("yyyy-MM-dd");
    
    private static final SimpleDateFormat DISPLAY_DATE_FORMAT = 
        new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Get today's date as string in yyyy-MM-dd format
     */
    public static String getTodayDateSQL() {
        return DATE_FORMAT.format(new Date());
    }
    
    /**
     * Get today's date as string in dd/MM/yyyy format
     */
    public static String getTodayDateDisplay() {
        return DISPLAY_DATE_FORMAT.format(new Date());
    }
    
    /**
     * Convert Date object to string in yyyy-MM-dd format
     */
    public static String dateToSQL(Date date) {
        if (date == null) return "";
        return DATE_FORMAT.format(date);
    }
    
    /**
     * Convert Date object to string in dd/MM/yyyy format
     */
    public static String dateToDisplay(Date date) {
        if (date == null) return "";
        return DISPLAY_DATE_FORMAT.format(date);
    }
    
    /**
     * Convert string to Date object
     */
    public static Date stringToDate(String dateStr) {
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Add days to a date
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
    
    /**
     * Add months to a date
     */
    public static Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
    
    /**
     * Check if date is in the past
     */
    public static boolean isPastDate(Date date) {
        return date.before(new Date());
    }
    
    /**
     * Check if date is in the future
     */
    public static boolean isFutureDate(Date date) {
        return date.after(new Date());
    }
    
    /**
     * Get difference in days between two dates
     */
    public static long getDaysDifference(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
    }
    
    /**
     * Get difference in months between two dates
     */
    public static int getMonthsDifference(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        
        int months = 0;
        while (start.before(end)) {
            start.add(Calendar.MONTH, 1);
            if (start.before(end) || start.equals(end)) {
                months++;
            }
        }
        return months;
    }
}

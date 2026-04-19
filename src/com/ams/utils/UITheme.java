package com.ams.utils;

import javax.swing.*;
import java.awt.*;

/**
 * UITheme - Professional theme and UI constants for the application
 * Provides consistent styling across all UI components
 */
public class UITheme {
    
    // Color Palette
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Professional Blue
    public static final Color PRIMARY_DARK = new Color(30, 90, 130);        // Dark Blue
    public static final Color PRIMARY_LIGHT = new Color(52, 152, 219);      // Light Blue
    
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);      // Green
    public static final Color WARNING_COLOR = new Color(241, 196, 15);      // Orange
    public static final Color DANGER_COLOR = new Color(231, 76, 60);        // Red
    public static final Color INFO_COLOR = new Color(52, 152, 219);         // Info Blue
    
    public static final Color BACKGROUND = new Color(240, 244, 247);        // Light Gray
    public static final Color SURFACE = Color.WHITE;
    public static final Color BORDER_COLOR = new Color(220, 227, 233);      // Light Border
    
    public static final Color TEXT_PRIMARY = new Color(44, 62, 80);         // Dark Gray
    public static final Color TEXT_SECONDARY = new Color(127, 140, 141);    // Medium Gray
    public static final Color TEXT_LIGHT = new Color(189, 195, 199);        // Light Gray
    
    public static final Color HOVER_COLOR = new Color(236, 240, 241);       // Hover Background
    public static final Color SELECTED_COLOR = new Color(52, 152, 219);     // Selected Background
    
    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 12);
    
    // Dimensions
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;
    public static final int CORNER_RADIUS = 8;
    public static final int PADDING_SMALL = 5;
    public static final int PADDING_MEDIUM = 10;
    public static final int PADDING_LARGE = 20;
    
    /**
     * Apply professional styling to a button
     */
    public static void styleButton(JButton button, Color bgColor, Color textColor) {
        button.setFont(FONT_BUTTON);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        button.setOpaque(true);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    /**
     * Apply professional styling to a primary button
     */
    public static void stylePrimaryButton(JButton button) {
        styleButton(button, PRIMARY_COLOR, Color.WHITE);
    }
    
    /**
     * Apply professional styling to a secondary button
     */
    public static void styleSecondaryButton(JButton button) {
        styleButton(button, BORDER_COLOR, TEXT_PRIMARY);
    }
    
    /**
     * Apply professional styling to a danger button
     */
    public static void styleDangerButton(JButton button) {
        styleButton(button, DANGER_COLOR, Color.WHITE);
    }
    
    /**
     * Apply professional styling to a text field
     */
    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setBackground(SURFACE);
        field.setForeground(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(250, 35));
    }
    
    /**
     * Apply professional styling to a label
     */
    public static void styleLabel(JLabel label, Font font, Color color) {
        label.setFont(font);
        label.setForeground(color);
    }
    
    /**
     * Apply professional styling to a panel
     */
    public static void stylePanel(JPanel panel, Color bgColor) {
        panel.setBackground(bgColor);
        panel.setOpaque(true);
    }
    
    /**
     * Create a professional card panel with shadow effect
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM)
        ));
        return panel;
    }
    
    /**
     * Create a professional header panel
     */
    public static JPanel createHeaderPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setPreferredSize(new Dimension(0, 70));
        panel.setLayout(new BorderLayout());
        
        JLabel label = new JLabel(title);
        label.setFont(FONT_HEADING);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, PADDING_LARGE, 0, 0));
        
        panel.add(label, BorderLayout.WEST);
        return panel;
    }
    
    /**
     * Create a stat card for dashboards
     */
    public static JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = createCardPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 120));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_SUBHEADING);
        titleLabel.setForeground(TEXT_SECONDARY);
        
        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(accentColor);
        
        // Accent bar
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accentColor);
        accentBar.setPreferredSize(new Dimension(5, 0));
        
        card.add(accentBar, BorderLayout.WEST);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
}

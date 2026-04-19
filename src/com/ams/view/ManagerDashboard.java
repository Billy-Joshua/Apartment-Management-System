package com.ams.view;

import com.ams.model.User;
import com.ams.utils.Constants;
import com.ams.utils.UITheme;
import javax.swing.*;
import java.awt.*;

/**
 * ManagerDashboard - Professional dashboard for Manager users
 * Managers can see limited tenants and rooms
 */
public class ManagerDashboard extends JFrame {
    
    private User user;
    private JTabbedPane tabbedPane;
    
    public ManagerDashboard(User user) {
        super(Constants.APP_TITLE + " - Manager Dashboard");
        this.user = user;
        initializeUI();
    }
    
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UITheme.WINDOW_WIDTH, UITheme.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        UITheme.stylePanel(headerPanel, UITheme.PRIMARY_DARK);
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, UITheme.PADDING_LARGE, 0, UITheme.PADDING_LARGE));
        
        JLabel welcomeLabel = new JLabel("👔 Manager: " + user.getUsername());
        welcomeLabel.setFont(UITheme.FONT_HEADING);
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        UITheme.styleDangerButton(logoutButton);
        logoutButton.addActionListener(e -> logout());
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(UITheme.BACKGROUND);
        tabbedPane.setFont(UITheme.FONT_SUBHEADING);
        
        tabbedPane.addTab("👥 Tenants", new TenantManagementUI().getPanel());
        tabbedPane.addTab("🏢 Available Rooms", new RoomManagementUI().getVacantRoomsPanel());
        tabbedPane.addTab("💳 Payments", new PaymentManagementUI().getPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}

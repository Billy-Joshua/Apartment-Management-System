package com.ams.view;

import com.ams.model.User;
import com.ams.utils.Constants;
import javax.swing.*;
import java.awt.*;

/**
 * ManagerDashboard - Dashboard for Manager users
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
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setPreferredSize(new Dimension(0, 50));
        JLabel welcomeLabel = new JLabel("Manager - " + user.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());
        
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(logoutButton);
        headerPanel.add(Box.createHorizontalStrut(20));
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tenants", new TenantManagementUI().getPanel());
        tabbedPane.addTab("Available Rooms", new RoomManagementUI().getVacantRoomsPanel());
        tabbedPane.addTab("Payments", new PaymentManagementUI().getPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}

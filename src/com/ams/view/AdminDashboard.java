package com.ams.view;

import com.ams.controller.*;
import com.ams.model.User;
import com.ams.utils.Constants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * AdminDashboard - Main dashboard for Admin users
 * Provides access to all system functionalities
 */
public class AdminDashboard extends JFrame {
    
    private User user;
    private JTabbedPane tabbedPane;
    private AuthController authController;
    private TenantController tenantController;
    private RoomController roomController;
    private PaymentController paymentController;
    private ContractController contractController;
    
    public AdminDashboard(User user) {
        super(Constants.APP_TITLE + " - Admin Dashboard");
        this.user = user;
        this.authController = new AuthController();
        this.tenantController = new TenantController();
        this.roomController = new RoomController();
        this.paymentController = new PaymentController();
        this.contractController = new ContractController();
        initializeUI();
    }
    
    /**
     * Initialize admin dashboard components
     */
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(0, 50));
        JLabel welcomeLabel = new JLabel("Welcome, Admin " + user.getUsername());
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
        
        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Tenants", new TenantManagementUI().getPanel());
        tabbedPane.addTab("Rooms", new RoomManagementUI().getPanel());
        tabbedPane.addTab("Payments", new PaymentManagementUI().getPanel());
        tabbedPane.addTab("Contracts", new ContractManagementUI().getPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    /**
     * Create dashboard panel with statistics
     */
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Total Tenants
        JPanel tenantsPanel = createStatPanel("Total Tenants", 
            String.valueOf(tenantController.getAllTenants().size()), 
            new Color(52, 152, 219));
        
        // Total Rooms
        JPanel roomsPanel = createStatPanel("Total Rooms", 
            String.valueOf(roomController.getAllRooms().size()), 
            new Color(46, 204, 113));
        
        // Pending Payments
        long pendingCount = paymentController.getAllPayments().stream()
            .filter(p -> "PENDING".equals(p.getStatus())).count();
        JPanel paymentsPanel = createStatPanel("Pending Payments", 
            String.valueOf(pendingCount), 
            new Color(230, 126, 34));
        
        // System Status
        JPanel statusPanel = createStatPanel("System Status", "OPERATIONAL", 
            new Color(155, 89, 182));
        
        panel.add(tenantsPanel);
        panel.add(roomsPanel);
        panel.add(paymentsPanel);
        panel.add(statusPanel);
        
        return panel;
    }
    
    /**
     * Create a statistics panel
     */
    private JPanel createStatPanel(String title, String value, Color bgColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel);
        panel.add(valueLabel);
        
        return panel;
    }
    
    /**
     * Logout and return to login screen
     */
    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}

package com.ams.view;

import com.ams.controller.*;
import com.ams.model.User;
import com.ams.utils.Constants;
import com.ams.utils.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * AdminDashboard - Professional dashboard for Admin users
 * Provides access to all system functionalities with modern UI
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
     * Initialize admin dashboard components with professional styling
     */
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UITheme.WINDOW_WIDTH, UITheme.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        UITheme.stylePanel(headerPanel, UITheme.PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, UITheme.PADDING_LARGE, 0, UITheme.PADDING_LARGE));
        
        JLabel welcomeLabel = new JLabel("👨‍💼 Welcome, Admin: " + user.getUsername());
        welcomeLabel.setFont(UITheme.FONT_HEADING);
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        UITheme.styleDangerButton(logoutButton);
        logoutButton.addActionListener(e -> logout());
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed Pane with professional styling
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(UITheme.BACKGROUND);
        tabbedPane.setFont(UITheme.FONT_SUBHEADING);
        
        tabbedPane.addTab("📊 Dashboard", createDashboardPanel());
        tabbedPane.addTab("👥 Tenants", new TenantManagementUI().getPanel());
        tabbedPane.addTab("🏢 Rooms", new RoomManagementUI().getPanel());
        tabbedPane.addTab("💳 Payments", new PaymentManagementUI().getPanel());
        tabbedPane.addTab("📄 Contracts", new ContractManagementUI().getPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    /**
     * Create professional dashboard panel with statistics
     */
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        UITheme.stylePanel(panel, UITheme.BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        // Statistics Panel
        JPanel statsPanel = new JPanel();
        UITheme.stylePanel(statsPanel, UITheme.BACKGROUND);
        statsPanel.setLayout(new GridLayout(2, 2, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        // Total Tenants
        statsPanel.add(UITheme.createStatCard("Total Tenants", 
            String.valueOf(tenantController.getAllTenants().size()), 
            UITheme.PRIMARY_LIGHT));
        
        // Total Rooms
        statsPanel.add(UITheme.createStatCard("Total Rooms", 
            String.valueOf(roomController.getAllRooms().size()), 
            UITheme.SUCCESS_COLOR));
        
        // Pending Payments
        long pendingCount = paymentController.getAllPayments().stream()
            .filter(p -> "PENDING".equals(p.getStatus())).count();
        statsPanel.add(UITheme.createStatCard("Pending Payments", 
            String.valueOf(pendingCount), 
            UITheme.WARNING_COLOR));
        
        // Active Contracts
        long activeContracts = contractController.getAllContracts().stream()
            .filter(c -> "ACTIVE".equals(c.getStatus())).count();
        statsPanel.add(UITheme.createStatCard("Active Contracts", 
            String.valueOf(activeContracts), 
            UITheme.INFO_COLOR));
        
        panel.add(statsPanel, BorderLayout.CENTER);
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

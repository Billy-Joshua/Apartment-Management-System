package com.ams.view;

import com.ams.controller.TenantController;
import com.ams.controller.PaymentController;
import com.ams.controller.ContractController;
import com.ams.model.User;
import com.ams.model.Tenant;
import com.ams.model.Payment;
import com.ams.model.Contract;
import com.ams.utils.Constants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * TenantDashboard - Dashboard for Tenant users
 * Tenants can view their own information, payments, and contracts
 */
public class TenantDashboard extends JFrame {
    
    private User user;
    private TenantController tenantController;
    private PaymentController paymentController;
    private ContractController contractController;
    private Tenant tenant;
    
    public TenantDashboard(User user) {
        super(Constants.APP_TITLE + " - Tenant Portal");
        this.user = user;
        this.tenantController = new TenantController();
        this.paymentController = new PaymentController();
        this.contractController = new ContractController();
        this.tenant = tenantController.getTenantByUserId(user.getUserId());
        initializeUI();
    }
    
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(39, 174, 96));
        headerPanel.setPreferredSize(new Dimension(0, 50));
        JLabel welcomeLabel = new JLabel("Welcome, " + (tenant != null ? tenant.getFullName() : user.getUsername()));
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
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Profile", createProfilePanel());
        tabbedPane.addTab("Payments", createPaymentsPanel());
        tabbedPane.addTab("Contract", createContractPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        if (tenant != null) {
            panel.add(new JLabel("Name:"));
            panel.add(new JLabel(tenant.getFullName()));
            
            panel.add(new JLabel("Email:"));
            panel.add(new JLabel(tenant.getEmail()));
            
            panel.add(new JLabel("Phone:"));
            panel.add(new JLabel(tenant.getPhone()));
            
            panel.add(new JLabel("Room ID:"));
            panel.add(new JLabel(String.valueOf(tenant.getRoomId())));
            
            panel.add(new JLabel("Move In Date:"));
            panel.add(new JLabel(tenant.getMoveInDate() != null ? tenant.getMoveInDate() : "N/A"));
            
            panel.add(new JLabel("Emergency Contact:"));
            panel.add(new JLabel(tenant.getEmergencyContact() != null ? tenant.getEmergencyContact() : "N/A"));
        }
        
        return panel;
    }
    
    private JPanel createPaymentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        if (tenant != null) {
            List<Payment> payments = paymentController.getPaymentsByTenant(tenant.getTenantId());
            
            String[] columns = {"Payment ID", "Amount", "Due Date", "Status", "Method"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            
            for (Payment p : payments) {
                model.addRow(new Object[]{
                    p.getPaymentId(),
                    "₹" + p.getAmount(),
                    p.getDueDate(),
                    p.getStatus(),
                    p.getPaymentMethod() != null ? p.getPaymentMethod() : "N/A"
                });
            }
            
            JTable table = new JTable(model);
            panel.add(new JScrollPane(table), BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    private JPanel createContractPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        if (tenant != null) {
            Contract contract = contractController.getContractByTenant(tenant.getTenantId());
            
            if (contract != null) {
                panel.add(new JLabel("Contract ID:"));
                panel.add(new JLabel(String.valueOf(contract.getContractId())));
                
                panel.add(new JLabel("Start Date:"));
                panel.add(new JLabel(contract.getStartDate()));
                
                panel.add(new JLabel("End Date:"));
                panel.add(new JLabel(contract.getEndDate()));
                
                panel.add(new JLabel("Monthly Rent:"));
                panel.add(new JLabel("₹" + contract.getMonthlyRent()));
                
                panel.add(new JLabel("Status:"));
                panel.add(new JLabel(contract.getStatus()));
            } else {
                panel.add(new JLabel("No active contract found"));
            }
        }
        
        return panel;
    }
    
    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}

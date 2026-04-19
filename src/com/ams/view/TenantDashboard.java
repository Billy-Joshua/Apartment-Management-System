package com.ams.view;

import com.ams.controller.TenantController;
import com.ams.controller.PaymentController;
import com.ams.controller.ContractController;
import com.ams.model.User;
import com.ams.model.Tenant;
import com.ams.model.Payment;
import com.ams.model.Contract;
import com.ams.utils.Constants;
import com.ams.utils.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * TenantDashboard - Professional dashboard for Tenant users
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
        setSize(UITheme.WINDOW_WIDTH, UITheme.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        UITheme.stylePanel(headerPanel, UITheme.SUCCESS_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, UITheme.PADDING_LARGE, 0, UITheme.PADDING_LARGE));
        
        JLabel welcomeLabel = new JLabel("👤 Welcome, " + (tenant != null ? tenant.getFullName() : user.getUsername()));
        welcomeLabel.setFont(UITheme.FONT_HEADING);
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        UITheme.styleDangerButton(logoutButton);
        logoutButton.addActionListener(e -> logout());
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(UITheme.BACKGROUND);
        tabbedPane.setFont(UITheme.FONT_SUBHEADING);
        
        tabbedPane.addTab("📋 Profile", createProfilePanel());
        tabbedPane.addTab("💳 Payments", createPaymentsPanel());
        tabbedPane.addTab("📄 Contract", createContractPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        UITheme.stylePanel(panel, UITheme.BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        JPanel cardPanel = UITheme.createCardPanel();
        cardPanel.setLayout(new GridLayout(6, 2, UITheme.PADDING_LARGE, UITheme.PADDING_MEDIUM));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        if (tenant != null) {
            addProfileRow(cardPanel, "👤 Name:", tenant.getFullName());
            addProfileRow(cardPanel, "📧 Email:", tenant.getEmail());
            addProfileRow(cardPanel, "☎️ Phone:", tenant.getPhone());
            addProfileRow(cardPanel, "🏠 Room ID:", String.valueOf(tenant.getRoomId()));
            addProfileRow(cardPanel, "📅 Move In Date:", tenant.getMoveInDate() != null ? tenant.getMoveInDate() : "N/A");
            addProfileRow(cardPanel, "🆘 Emergency Contact:", tenant.getEmergencyContact() != null ? tenant.getEmergencyContact() : "N/A");
        }
        
        panel.add(cardPanel, BorderLayout.NORTH);
        return panel;
    }
    
    private void addProfileRow(JPanel panel, String label, String value) {
        JLabel labelComp = new JLabel(label);
        UITheme.styleLabel(labelComp, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(labelComp);
        
        JLabel valueComp = new JLabel(value);
        UITheme.styleLabel(valueComp, UITheme.FONT_BODY, UITheme.TEXT_SECONDARY);
        panel.add(valueComp);
    }
    
    private JPanel createPaymentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        UITheme.stylePanel(panel, UITheme.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        if (tenant != null) {
            List<Payment> payments = paymentController.getPaymentsByTenant(tenant.getTenantId());
            
            String[] columns = {"Payment ID", "Amount", "Due Date", "Status", "Method"};
            DefaultTableModel model = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
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
            table.setRowHeight(25);
            table.setFont(UITheme.FONT_BODY);
            table.getTableHeader().setFont(UITheme.FONT_SUBHEADING);
            table.getTableHeader().setBackground(UITheme.PRIMARY_COLOR);
            table.getTableHeader().setForeground(Color.WHITE);
            table.setSelectionBackground(UITheme.SELECTED_COLOR);
            
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1), "Your Payments"),
                BorderFactory.createEmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
            ));
            
            panel.add(scrollPane, BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    private JPanel createContractPanel() {
        JPanel panel = new JPanel();
        UITheme.stylePanel(panel, UITheme.BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        JPanel cardPanel = UITheme.createCardPanel();
        cardPanel.setLayout(new GridLayout(5, 2, UITheme.PADDING_LARGE, UITheme.PADDING_MEDIUM));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        if (tenant != null) {
            Contract contract = contractController.getContractByTenant(tenant.getTenantId());
            
            if (contract != null) {
                addContractRow(cardPanel, "📝 Contract ID:", String.valueOf(contract.getContractId()));
                addContractRow(cardPanel, "📅 Start Date:", contract.getStartDate());
                addContractRow(cardPanel, "📅 End Date:", contract.getEndDate());
                addContractRow(cardPanel, "💰 Monthly Rent:", "₹" + contract.getMonthlyRent());
                addContractRow(cardPanel, "✅ Status:", contract.getStatus());
            } else {
                JLabel noContractLabel = new JLabel("ℹ️ No active contract found");
                noContractLabel.setFont(UITheme.FONT_BODY);
                noContractLabel.setForeground(UITheme.WARNING_COLOR);
                cardPanel.add(noContractLabel);
            }
        }
        
        panel.add(cardPanel, BorderLayout.NORTH);
        return panel;
    }
    
    private void addContractRow(JPanel panel, String label, String value) {
        JLabel labelComp = new JLabel(label);
        UITheme.styleLabel(labelComp, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(labelComp);
        
        JLabel valueComp = new JLabel(value);
        UITheme.styleLabel(valueComp, UITheme.FONT_BODY, UITheme.TEXT_SECONDARY);
        panel.add(valueComp);
    }
    
    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}

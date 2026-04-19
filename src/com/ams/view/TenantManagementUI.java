package com.ams.view;

import com.ams.controller.TenantController;
import com.ams.model.Tenant;
import com.ams.utils.UITheme;
import com.ams.utils.ValidationUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * TenantManagementUI - Professional UI for managing tenants (Create, Read, Update, Delete)
 */
public class TenantManagementUI {
    
    private TenantController tenantController;
    private JPanel mainPanel;
    private JTable tenantsTable;
    private DefaultTableModel tableModel;
    private JTextField firstNameField, lastNameField, emailField, phoneField, emergencyContactField, emergencyPhoneField;
    private JSpinner roomIdSpinner;
    
    public TenantManagementUI() {
        this.tenantController = new TenantController();
        initializePanel();
    }
    
    /**
     * Initialize the tenant management panel with professional styling
     */
    private void initializePanel() {
        mainPanel = new JPanel(new BorderLayout(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        // Form Panel
        JPanel formPanel = UITheme.createCardPanel();
        formPanel.setLayout(new GridLayout(3, 4, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1), "Add/Edit Tenant"),
            BorderFactory.createEmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
        ));
        
        // First Name
        formPanel.add(createLabeledComponent("First Name:", (firstNameField = new JTextField())));
        UITheme.styleTextField(firstNameField);
        
        // Last Name
        formPanel.add(createLabeledComponent("Last Name:", (lastNameField = new JTextField())));
        UITheme.styleTextField(lastNameField);
        
        // Email
        formPanel.add(createLabeledComponent("Email:", (emailField = new JTextField())));
        UITheme.styleTextField(emailField);
        
        // Phone
        formPanel.add(createLabeledComponent("Phone:", (phoneField = new JTextField())));
        UITheme.styleTextField(phoneField);
        
        // Emergency Contact
        formPanel.add(createLabeledComponent("Emergency Contact:", (emergencyContactField = new JTextField())));
        UITheme.styleTextField(emergencyContactField);
        
        // Emergency Phone
        formPanel.add(createLabeledComponent("Emergency Phone:", (emergencyPhoneField = new JTextField())));
        UITheme.styleTextField(emergencyPhoneField);
        
        // Room ID
        roomIdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        formPanel.add(createLabeledComponent("Room ID:", roomIdSpinner));
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, UITheme.PADDING_MEDIUM, 0));
        UITheme.stylePanel(buttonPanel, UITheme.SURFACE);
        
        JButton saveButton = new JButton("💾 Save Tenant");
        UITheme.stylePrimaryButton(saveButton);
        saveButton.addActionListener(this::saveTenant);
        buttonPanel.add(saveButton);
        
        JButton deleteButton = new JButton("🗑️ Delete");
        UITheme.styleDangerButton(deleteButton);
        deleteButton.addActionListener(this::deleteTenant);
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("🔄 Refresh");
        UITheme.styleSecondaryButton(refreshButton);
        refreshButton.addActionListener(e -> refreshTable());
        buttonPanel.add(refreshButton);
        
        formPanel.add(buttonPanel);
        
        // Table Panel
        String[] columns = {"ID", "Name", "Email", "Phone", "Room ID", "Move In Date", "Emergency Contact"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tenantsTable = new JTable(tableModel);
        tenantsTable.setRowHeight(25);
        tenantsTable.setFont(UITheme.FONT_BODY);
        tenantsTable.getTableHeader().setFont(UITheme.FONT_SUBHEADING);
        tenantsTable.getTableHeader().setBackground(UITheme.PRIMARY_COLOR);
        tenantsTable.getTableHeader().setForeground(Color.WHITE);
        tenantsTable.setSelectionBackground(UITheme.SELECTED_COLOR);
        tenantsTable.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tenantsTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1), "Tenants List"),
            BorderFactory.createEmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
        ));
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        refreshTable();
    }
    
    private JPanel createLabeledComponent(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        UITheme.stylePanel(panel, UITheme.SURFACE);
        JLabel lbl = new JLabel(label);
        UITheme.styleLabel(lbl, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        panel.add(lbl, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Save tenant (insert or update)
     */
    private void saveTenant(ActionEvent e) {
        // Validate inputs
        if (ValidationUtils.isEmpty(firstNameField.getText()) || 
            ValidationUtils.isEmpty(emailField.getText())) {
            JOptionPane.showMessageDialog(mainPanel, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Tenant tenant = new Tenant(
            firstNameField.getText(),
            lastNameField.getText(),
            emailField.getText(),
            phoneField.getText(),
            (Integer) roomIdSpinner.getValue(),
            null
        );
        
        tenant.setEmergencyContact(emergencyContactField.getText());
        tenant.setEmergencyPhone(emergencyPhoneField.getText());
        
        if (tenantController.addTenant(tenant)) {
            JOptionPane.showMessageDialog(mainPanel, "✓ Tenant added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(mainPanel, "✗ Error adding tenant!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Delete tenant
     */
    private void deleteTenant(ActionEvent e) {
        int selectedRow = tenantsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a tenant to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int tenantId = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete this tenant?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (tenantController.deleteTenant(tenantId)) {
                JOptionPane.showMessageDialog(mainPanel, "✓ Tenant deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(mainPanel, "✗ Error deleting tenant!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Refresh table data
     */
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Tenant> tenants = tenantController.getAllTenants();
        
        for (Tenant t : tenants) {
            tableModel.addRow(new Object[]{
                t.getTenantId(),
                t.getFullName(),
                t.getEmail(),
                t.getPhone(),
                t.getRoomId(),
                t.getMoveInDate() != null ? t.getMoveInDate() : "N/A",
                t.getEmergencyContact() != null ? t.getEmergencyContact() : "N/A"
            });
        }
    }
    
    /**
     * Clear form fields
     */
    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        emergencyContactField.setText("");
        emergencyPhoneField.setText("");
        roomIdSpinner.setValue(1);
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
}

package com.ams.view;

import com.ams.controller.TenantController;
import com.ams.model.Tenant;
import com.ams.utils.ValidationUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * TenantManagementUI - UI for managing tenants (Create, Read, Update, Delete)
 */
public class TenantManagementUI {
    
    private TenantController tenantController;
    private JPanel mainPanel;
    private JTable tenantsTable;
    private DefaultTableModel tableModel;
    private JTextField firstNameField, lastNameField, emailField, phoneField;
    private JSpinner roomIdSpinner;
    
    public TenantManagementUI() {
        this.tenantController = new TenantController();
        initializePanel();
    }
    
    /**
     * Initialize the tenant management panel
     */
    private void initializePanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add/Edit Tenant"));
        
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);
        
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Room ID:"));
        roomIdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        formPanel.add(roomIdSpinner);
        
        JButton saveButton = new JButton("Save Tenant");
        saveButton.addActionListener(this::saveTenant);
        formPanel.add(saveButton);
        
        JButton deleteButton = new JButton("Delete Tenant");
        deleteButton.addActionListener(this::deleteTenant);
        formPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        formPanel.add(refreshButton);
        
        // Table Panel
        String[] columns = {"ID", "Name", "Email", "Phone", "Room ID", "Move In Date"};
        tableModel = new DefaultTableModel(columns, 0);
        tenantsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tenantsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tenants List"));
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        refreshTable();
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
        
        if (tenantController.addTenant(tenant)) {
            JOptionPane.showMessageDialog(mainPanel, "Tenant added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Error adding tenant!", "Error", JOptionPane.ERROR_MESSAGE);
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
        int confirm = JOptionPane.showConfirmDialog(mainPanel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (tenantController.deleteTenant(tenantId)) {
                JOptionPane.showMessageDialog(mainPanel, "Tenant deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
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
                t.getMoveInDate() != null ? t.getMoveInDate() : "N/A"
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
        roomIdSpinner.setValue(1);
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
}

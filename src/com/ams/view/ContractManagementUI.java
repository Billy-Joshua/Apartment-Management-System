package com.ams.view;

import com.ams.controller.ContractController;
import com.ams.model.Contract;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ContractManagementUI - UI for managing contracts
 */
public class ContractManagementUI {
    
    private ContractController contractController;
    private JPanel mainPanel;
    private JTable contractsTable;
    private DefaultTableModel tableModel;
    
    public ContractManagementUI() {
        this.contractController = new ContractController();
        initializePanel();
    }
    
    private void initializePanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table Panel
        String[] columns = {"Contract ID", "Tenant ID", "Room ID", "Start Date", "End Date", "Monthly Rent", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        contractsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(contractsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Contracts List"));
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewDetails());
        
        bottomPanel.add(refreshButton);
        bottomPanel.add(viewDetailsButton);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    private void viewDetails() {
        int selectedRow = contractsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a contract!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(mainPanel, "Contract details would be displayed here.", "Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Contract> contracts = contractController.getAllContracts();
        
        for (Contract c : contracts) {
            tableModel.addRow(new Object[]{
                c.getContractId(),
                c.getTenantId(),
                c.getRoomId(),
                c.getStartDate(),
                c.getEndDate(),
                "₹" + c.getMonthlyRent(),
                c.getStatus()
            });
        }
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
}

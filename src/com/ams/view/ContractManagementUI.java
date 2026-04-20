package com.ams.view;

import com.ams.controller.ContractController;
import com.ams.model.Contract;
import com.ams.utils.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ContractManagementUI - Professional UI for managing contracts
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
        mainPanel = new JPanel(new BorderLayout(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        // Table Panel
        String[] columns = {"Contract ID", "Tenant ID", "Room ID", "Start Date", "End Date", "Monthly Rent", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        contractsTable = new JTable(tableModel);
        contractsTable.setRowHeight(25);
        contractsTable.setFont(UITheme.FONT_BODY);
        contractsTable.getTableHeader().setFont(UITheme.FONT_SUBHEADING);
        contractsTable.getTableHeader().setBackground(UITheme.PRIMARY_COLOR);
        contractsTable.getTableHeader().setForeground(Color.WHITE);
        contractsTable.setSelectionBackground(UITheme.SELECTED_COLOR);
        contractsTable.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(contractsTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1), "Contracts List"),
            BorderFactory.createEmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
        ));
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        UITheme.stylePanel(bottomPanel, UITheme.BACKGROUND);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        
        JButton refreshButton = new JButton("🔄 Refresh");
        UITheme.styleSecondaryButton(refreshButton);
        refreshButton.addActionListener(e -> refreshTable());
        bottomPanel.add(refreshButton);
        
        JButton viewDetailsButton = new JButton("📄 View Details");
        UITheme.stylePrimaryButton(viewDetailsButton);
        viewDetailsButton.addActionListener(e -> viewDetails());
        bottomPanel.add(viewDetailsButton);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    private void viewDetails() {
        int selectedRow = contractsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a contract to view!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int contractId = (Integer) tableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(mainPanel, 
            "Contract ID: " + contractId + "\n" +
            "Contact details and options would be displayed here.",
            "Contract Details", JOptionPane.INFORMATION_MESSAGE);
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
                "$" + c.getMonthlyRent(),
                c.getStatus()
            });
        }
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
}

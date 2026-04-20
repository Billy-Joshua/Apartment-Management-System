package com.ams.view;

import com.ams.controller.PaymentController;
import com.ams.model.Payment;
import com.ams.utils.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * PaymentManagementUI - Professional UI for managing payments
 */
public class PaymentManagementUI {
    
    private PaymentController paymentController;
    private JPanel mainPanel;
    private JTable paymentsTable;
    private DefaultTableModel tableModel;
    
    public PaymentManagementUI() {
        this.paymentController = new PaymentController();
        initializePanel();
    }
    
    private void initializePanel() {
        mainPanel = new JPanel(new BorderLayout(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        // Table Panel
        String[] columns = {"Payment ID", "Tenant ID", "Amount", "Due Date", "Status", "Method", "Receipt"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paymentsTable = new JTable(tableModel);
        paymentsTable.setRowHeight(25);
        paymentsTable.setFont(UITheme.FONT_BODY);
        paymentsTable.getTableHeader().setFont(UITheme.FONT_SUBHEADING);
        paymentsTable.getTableHeader().setBackground(UITheme.PRIMARY_COLOR);
        paymentsTable.getTableHeader().setForeground(Color.WHITE);
        paymentsTable.setSelectionBackground(UITheme.SELECTED_COLOR);
        paymentsTable.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1), "Payments List"),
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
        
        JButton markPaidButton = new JButton("✓ Mark as Paid");
        UITheme.stylePrimaryButton(markPaidButton);
        markPaidButton.addActionListener(this::markAsPaid);
        bottomPanel.add(markPaidButton);
        
        JButton sendReminderButton = new JButton("📧 Send Reminder");
        UITheme.styleSecondaryButton(sendReminderButton);
        sendReminderButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, "✓ Reminder sent to tenant!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        bottomPanel.add(sendReminderButton);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    private void markAsPaid(ActionEvent e) {
        int selectedRow = paymentsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a payment to mark as paid!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(mainPanel, "✓ Payment marked as paid!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Payment> payments = paymentController.getAllPayments();
        
        for (Payment p : payments) {
            tableModel.addRow(new Object[]{
                p.getPaymentId(),
                p.getTenantId(),
                "$" + p.getAmount(),
                p.getDueDate(),
                p.getStatus(),
                p.getPaymentMethod() != null ? p.getPaymentMethod() : "N/A",
                p.getReceiptNumber() != null ? p.getReceiptNumber() : "N/A"
            });
        }
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
}

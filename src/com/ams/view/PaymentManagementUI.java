package com.ams.view;

import com.ams.controller.PaymentController;
import com.ams.model.Payment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * PaymentManagementUI - UI for managing payments
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
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table Panel
        String[] columns = {"Payment ID", "Tenant ID", "Amount", "Due Date", "Status", "Method", "Receipt"};
        tableModel = new DefaultTableModel(columns, 0);
        paymentsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Payments List"));
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        
        JButton markPaidButton = new JButton("Mark as Paid");
        markPaidButton.addActionListener(this::markAsPaid);
        
        JButton sendReminderButton = new JButton("Send Reminder");
        sendReminderButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, "Reminder sent to tenant!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        
        bottomPanel.add(refreshButton);
        bottomPanel.add(markPaidButton);
        bottomPanel.add(sendReminderButton);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    private void markAsPaid(ActionEvent e) {
        int selectedRow = paymentsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a payment!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(mainPanel, "Payment marked as paid!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Payment> payments = paymentController.getAllPayments();
        
        for (Payment p : payments) {
            tableModel.addRow(new Object[]{
                p.getPaymentId(),
                p.getTenantId(),
                "₹" + p.getAmount(),
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

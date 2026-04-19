package com.ams.view;

import com.ams.controller.RoomController;
import com.ams.model.Room;
import com.ams.utils.ValidationUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * RoomManagementUI - UI for managing rooms
 */
public class RoomManagementUI {
    
    private RoomController roomController;
    private JPanel mainPanel;
    private JTable roomsTable;
    private DefaultTableModel tableModel;
    private JTextField roomNumberField, roomTypeField;
    private JSpinner floorSpinner, rentSpinner;
    private JComboBox<String> statusCombo;
    
    public RoomManagementUI() {
        this.roomController = new RoomController();
        initializePanel();
    }
    
    private void initializePanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 6, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add/Edit Room"));
        
        formPanel.add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        formPanel.add(roomNumberField);
        
        formPanel.add(new JLabel("Floor:"));
        floorSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        formPanel.add(floorSpinner);
        
        formPanel.add(new JLabel("Room Type:"));
        roomTypeField = new JTextField();
        formPanel.add(roomTypeField);
        
        formPanel.add(new JLabel("Status:"));
        statusCombo = new JComboBox<>(new String[]{"VACANT", "OCCUPIED", "MAINTENANCE"});
        formPanel.add(statusCombo);
        
        formPanel.add(new JLabel("Monthly Rent:"));
        rentSpinner = new JSpinner(new SpinnerNumberModel(10000.0, 0.0, 1000000.0, 1000.0));
        formPanel.add(rentSpinner);
        
        JButton saveButton = new JButton("Save Room");
        saveButton.addActionListener(this::saveRoom);
        formPanel.add(saveButton);
        
        // Table Panel
        String[] columns = {"Room ID", "Room Number", "Floor", "Type", "Status", "Monthly Rent"};
        tableModel = new DefaultTableModel(columns, 0);
        roomsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rooms List"));
        
        JPanel bottomPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        JButton deleteButton = new JButton("Delete Room");
        deleteButton.addActionListener(this::deleteRoom);
        bottomPanel.add(refreshButton);
        bottomPanel.add(deleteButton);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        refreshTable();
    }
    
    private void saveRoom(ActionEvent e) {
        if (ValidationUtils.isEmpty(roomNumberField.getText())) {
            JOptionPane.showMessageDialog(mainPanel, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Room room = new Room(
            roomNumberField.getText(),
            (Integer) floorSpinner.getValue(),
            roomTypeField.getText(),
            (String) statusCombo.getSelectedItem(),
            (Double) rentSpinner.getValue()
        );
        
        if (roomController.addRoom(room)) {
            JOptionPane.showMessageDialog(mainPanel, "Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        }
    }
    
    private void deleteRoom(ActionEvent e) {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a room!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int roomId = (Integer) tableModel.getValueAt(selectedRow, 0);
        if (roomController.deleteRoom(roomId)) {
            refreshTable();
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Room> rooms = roomController.getAllRooms();
        
        for (Room r : rooms) {
            tableModel.addRow(new Object[]{
                r.getRoomId(),
                r.getRoomNumber(),
                r.getFloor(),
                r.getRoomType(),
                r.getStatus(),
                "₹" + r.getMonthlyRent()
            });
        }
    }
    
    private void clearForm() {
        roomNumberField.setText("");
        floorSpinner.setValue(1);
        roomTypeField.setText("");
        statusCombo.setSelectedIndex(0);
        rentSpinner.setValue(10000.0);
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
    
    public JPanel getVacantRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Room ID", "Room Number", "Floor", "Type", "Monthly Rent"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Room r : roomController.getVacantRooms()) {
            model.addRow(new Object[]{
                r.getRoomId(),
                r.getRoomNumber(),
                r.getFloor(),
                r.getRoomType(),
                "₹" + r.getMonthlyRent()
            });
        }
        
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}

package com.ams.view;

import com.ams.controller.RoomController;
import com.ams.model.Room;
import com.ams.utils.UITheme;
import com.ams.utils.ValidationUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * RoomManagementUI - Professional UI for managing rooms
 */
public class RoomManagementUI {
    
    private RoomController roomController;
    private JPanel mainPanel;
    private JTable roomsTable;
    private DefaultTableModel tableModel;
    private JTextField roomNumberField, roomTypeField;
    private JSpinner floorSpinner, rentSpinner;
    private JComboBox<String> statusCombo;
    private Integer selectedRoomId = null;  // Track if editing
    
    public RoomManagementUI() {
        this.roomController = new RoomController();
        initializePanel();
    }
    
    private void initializePanel() {
        mainPanel = new JPanel(new BorderLayout(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        
        // Form Panel
        JPanel formPanel = UITheme.createCardPanel();
        formPanel.setLayout(new GridLayout(2, 6, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1), "Add/Edit Room"),
            BorderFactory.createEmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
        ));
        
        // Room Number
        JLabel roomNumberLabel = new JLabel("Room Number:");
        UITheme.styleLabel(roomNumberLabel, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        formPanel.add(roomNumberLabel);
        roomNumberField = new JTextField();
        UITheme.styleTextField(roomNumberField);
        formPanel.add(roomNumberField);
        
        // Floor
        JLabel floorLabel = new JLabel("Floor:");
        UITheme.styleLabel(floorLabel, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        formPanel.add(floorLabel);
        floorSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        formPanel.add(floorSpinner);
        
        // Room Type
        JLabel typeLabel = new JLabel("Room Type:");
        UITheme.styleLabel(typeLabel, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        formPanel.add(typeLabel);
        roomTypeField = new JTextField();
        UITheme.styleTextField(roomTypeField);
        formPanel.add(roomTypeField);
        
        // Status
        JLabel statusLabel = new JLabel("Status:");
        UITheme.styleLabel(statusLabel, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        formPanel.add(statusLabel);
        statusCombo = new JComboBox<>(new String[]{"VACANT", "OCCUPIED", "MAINTENANCE"});
        statusCombo.setFont(UITheme.FONT_BODY);
        formPanel.add(statusCombo);
        
        // Monthly Rent
        JLabel rentLabel = new JLabel("Monthly Rent:");
        UITheme.styleLabel(rentLabel, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        formPanel.add(rentLabel);
        rentSpinner = new JSpinner(new SpinnerNumberModel(10000.0, 0.0, 1000000.0, 1000.0));
        formPanel.add(rentSpinner);
        
        // Save Button
        JButton saveButton = new JButton("💾 Save Room");
        UITheme.stylePrimaryButton(saveButton);
        saveButton.addActionListener(this::saveRoom);
        formPanel.add(saveButton);
        
        // Table Panel
        String[] columns = {"Room ID", "Room Number", "Floor", "Type", "Status", "Monthly Rent"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomsTable = new JTable(tableModel);
        roomsTable.setRowHeight(25);
        roomsTable.setFont(UITheme.FONT_BODY);
        roomsTable.getTableHeader().setFont(UITheme.FONT_SUBHEADING);
        roomsTable.getTableHeader().setBackground(UITheme.PRIMARY_COLOR);
        roomsTable.getTableHeader().setForeground(Color.WHITE);
        roomsTable.setSelectionBackground(UITheme.SELECTED_COLOR);
        roomsTable.setSelectionForeground(Color.WHITE);
        
        // Add row selection listener to populate form
        roomsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && roomsTable.getSelectedRow() >= 0) {
                loadRoomToForm(roomsTable.getSelectedRow());
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1), "Rooms List"),
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
        
        JButton editButton = new JButton("✏️ Edit");
        UITheme.stylePrimaryButton(editButton);
        editButton.addActionListener(this::editRoom);
        bottomPanel.add(editButton);
        
        JButton deleteButton = new JButton("🗑️ Delete");
        UITheme.styleDangerButton(deleteButton);
        deleteButton.addActionListener(this::deleteRoom);
        bottomPanel.add(deleteButton);
        
        JButton clearButton = new JButton("🔄 Clear");
        UITheme.styleSecondaryButton(clearButton);
        clearButton.addActionListener(e -> clearForm());
        bottomPanel.add(clearButton);
        
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
        
        if (selectedRoomId != null) {
            // Update existing room
            room.setRoomId(selectedRoomId);
            if (roomController.updateRoom(room)) {
                JOptionPane.showMessageDialog(mainPanel, "✓ Room updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(mainPanel, "✗ Error updating room!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Add new room
            if (roomController.addRoom(room)) {
                JOptionPane.showMessageDialog(mainPanel, "✓ Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(mainPanel, "✗ Error adding room!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Edit room (load selected row into form)
     */
    private void editRoom(ActionEvent e) {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a room to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        loadRoomToForm(selectedRow);
    }
    
    /**
     * Load room data into form fields
     */
    private void loadRoomToForm(int row) {
        selectedRoomId = (Integer) tableModel.getValueAt(row, 0);
        Room room = roomController.getRoomById(selectedRoomId);
        
        if (room != null) {
            roomNumberField.setText(room.getRoomNumber());
            floorSpinner.setValue(room.getFloor());
            roomTypeField.setText(room.getRoomType());
            statusCombo.setSelectedItem(room.getStatus());
            rentSpinner.setValue(room.getMonthlyRent());
        }
    }
    
    private void deleteRoom(ActionEvent e) {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a room to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int roomId = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete this room?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (roomController.deleteRoom(roomId)) {
                JOptionPane.showMessageDialog(mainPanel, "✓ Room deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            }
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
        selectedRoomId = null;  // Reset edit mode
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

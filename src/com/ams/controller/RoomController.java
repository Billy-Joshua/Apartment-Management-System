package com.ams.controller;

import com.ams.config.DatabaseConfig;
import com.ams.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RoomController - Handles room management operations
 */
public class RoomController {
    
    private Connection conn;
    
    public RoomController() {
        this.conn = DatabaseConfig.getConnection();
    }
    
    /**
     * Add new room
     */
    public boolean addRoom(Room room) {
        try {
            String query = "INSERT INTO rooms (room_number, floor, room_type, status, monthly_rent, description) " +
                          "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, room.getRoomNumber());
            ps.setInt(2, room.getFloor());
            ps.setString(3, room.getRoomType());
            ps.setString(4, room.getStatus());
            ps.setDouble(5, room.getMonthlyRent());
            ps.setString(6, room.getDescription());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update room
     */
    public boolean updateRoom(Room room) {
        try {
            String query = "UPDATE rooms SET room_number=?, floor=?, room_type=?, status=?, " +
                          "monthly_rent=?, description=? WHERE room_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, room.getRoomNumber());
            ps.setInt(2, room.getFloor());
            ps.setString(3, room.getRoomType());
            ps.setString(4, room.getStatus());
            ps.setDouble(5, room.getMonthlyRent());
            ps.setString(6, room.getDescription());
            ps.setInt(7, room.getRoomId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete room
     */
    public boolean deleteRoom(int roomId) {
        try {
            String query = "DELETE FROM rooms WHERE room_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, roomId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all rooms
     */
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try {
            String query = "SELECT * FROM rooms ORDER BY room_number";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                rooms.add(new Room(
                    rs.getInt("room_id"),
                    rs.getString("room_number"),
                    rs.getInt("floor"),
                    rs.getString("room_type"),
                    rs.getString("status"),
                    rs.getDouble("monthly_rent"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
        }
        return rooms;
    }
    
    /**
     * Get vacant rooms
     */
    public List<Room> getVacantRooms() {
        List<Room> rooms = new ArrayList<>();
        try {
            String query = "SELECT * FROM rooms WHERE status = 'VACANT' ORDER BY room_number";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                rooms.add(new Room(
                    rs.getInt("room_id"),
                    rs.getString("room_number"),
                    rs.getInt("floor"),
                    rs.getString("room_type"),
                    rs.getString("status"),
                    rs.getDouble("monthly_rent"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vacant rooms: " + e.getMessage());
        }
        return rooms;
    }
    
    /**
     * Get room by ID
     */
    public Room getRoomById(int roomId) {
        try {
            String query = "SELECT * FROM rooms WHERE room_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, roomId);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Room(
                    rs.getInt("room_id"),
                    rs.getString("room_number"),
                    rs.getInt("floor"),
                    rs.getString("room_type"),
                    rs.getString("status"),
                    rs.getDouble("monthly_rent"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching room: " + e.getMessage());
        }
        return null;
    }
}

package com.ams.model;

import java.io.Serializable;

/**
 * Room model class - Represents a room/apartment
 */
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int roomId;
    private String roomNumber;
    private int floor;
    private String roomType;
    private String status; // VACANT, OCCUPIED, MAINTENANCE
    private double monthlyRent;
    private String description;
    
    /**
     * Default constructor
     */
    public Room() {
    }
    
    /**
     * Constructor with parameters
     */
    public Room(String roomNumber, int floor, String roomType, String status, double monthlyRent) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomType = roomType;
        this.status = status;
        this.monthlyRent = monthlyRent;
    }
    
    /**
     * Full constructor
     */
    public Room(int roomId, String roomNumber, int floor, String roomType, String status, double monthlyRent, String description) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomType = roomType;
        this.status = status;
        this.monthlyRent = monthlyRent;
        this.description = description;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public void setFloor(int floor) {
        this.floor = floor;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getMonthlyRent() {
        return monthlyRent;
    }
    
    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomNumber='" + roomNumber + '\'' +
                ", floor=" + floor +
                ", roomType='" + roomType + '\'' +
                ", status='" + status + '\'' +
                ", monthlyRent=" + monthlyRent +
                '}';
    }
}

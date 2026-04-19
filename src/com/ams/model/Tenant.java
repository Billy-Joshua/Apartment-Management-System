package com.ams.model;

import java.io.Serializable;

/**
 * Tenant model class - Represents a tenant
 */
public class Tenant implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int tenantId;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int roomId;
    private String moveInDate;
    private String emergencyContact;
    private String emergencyPhone;
    
    /**
     * Default constructor
     */
    public Tenant() {
    }
    
    /**
     * Constructor with parameters
     */
    public Tenant(String firstName, String lastName, String email, String phone, int roomId, String moveInDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.roomId = roomId;
        this.moveInDate = moveInDate;
    }
    
    /**
     * Full constructor
     */
    public Tenant(int tenantId, int userId, String firstName, String lastName, String email, String phone, 
                  int roomId, String moveInDate, String emergencyContact, String emergencyPhone) {
        this.tenantId = tenantId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.roomId = roomId;
        this.moveInDate = moveInDate;
        this.emergencyContact = emergencyContact;
        this.emergencyPhone = emergencyPhone;
    }
    
    // Getters and Setters
    public int getTenantId() {
        return tenantId;
    }
    
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getMoveInDate() {
        return moveInDate;
    }
    
    public void setMoveInDate(String moveInDate) {
        this.moveInDate = moveInDate;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public String getEmergencyPhone() {
        return emergencyPhone;
    }
    
    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "Tenant{" +
                "tenantId=" + tenantId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roomId=" + roomId +
                '}';
    }
}

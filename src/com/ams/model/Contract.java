package com.ams.model;

import java.io.Serializable;

/**
 * Contract model class - Represents a lease contract
 */
public class Contract implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int contractId;
    private int tenantId;
    private int roomId;
    private String startDate;
    private String endDate;
    private double monthlyRent;
    private double securityDeposit;
    private String terms;
    private String status; // ACTIVE, EXPIRED, TERMINATED
    
    /**
     * Default constructor
     */
    public Contract() {
    }
    
    /**
     * Constructor with parameters
     */
    public Contract(int tenantId, int roomId, String startDate, String endDate, double monthlyRent, double securityDeposit) {
        this.tenantId = tenantId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
        this.securityDeposit = securityDeposit;
        this.status = "ACTIVE";
    }
    
    /**
     * Full constructor
     */
    public Contract(int contractId, int tenantId, int roomId, String startDate, String endDate, 
                    double monthlyRent, double securityDeposit, String terms, String status) {
        this.contractId = contractId;
        this.tenantId = tenantId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
        this.securityDeposit = securityDeposit;
        this.terms = terms;
        this.status = status;
    }
    
    // Getters and Setters
    public int getContractId() {
        return contractId;
    }
    
    public void setContractId(int contractId) {
        this.contractId = contractId;
    }
    
    public int getTenantId() {
        return tenantId;
    }
    
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
    
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public double getMonthlyRent() {
        return monthlyRent;
    }
    
    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }
    
    public double getSecurityDeposit() {
        return securityDeposit;
    }
    
    public void setSecurityDeposit(double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }
    
    public String getTerms() {
        return terms;
    }
    
    public void setTerms(String terms) {
        this.terms = terms;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", tenantId=" + tenantId +
                ", roomId=" + roomId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", monthlyRent=" + monthlyRent +
                ", status='" + status + '\'' +
                '}';
    }
}

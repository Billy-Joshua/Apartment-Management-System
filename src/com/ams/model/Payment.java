package com.ams.model;

import java.io.Serializable;

/**
 * Payment model class - Represents a rent payment
 */
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int paymentId;
    private int tenantId;
    private int contractId;
    private double amount;
    private String paymentDate;
    private String dueDate;
    private String status; // PAID, PENDING, OVERDUE
    private String paymentMethod;
    private String receiptNumber;
    private String notes;
    
    /**
     * Default constructor
     */
    public Payment() {
    }
    
    /**
     * Constructor with parameters
     */
    public Payment(int tenantId, int contractId, double amount, String dueDate, String status) {
        this.tenantId = tenantId;
        this.contractId = contractId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }
    
    /**
     * Full constructor
     */
    public Payment(int paymentId, int tenantId, int contractId, double amount, String paymentDate, 
                   String dueDate, String status, String paymentMethod, String receiptNumber) {
        this.paymentId = paymentId;
        this.tenantId = tenantId;
        this.contractId = contractId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
    }
    
    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    
    public int getTenantId() {
        return tenantId;
    }
    
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
    
    public int getContractId() {
        return contractId;
    }
    
    public void setContractId(int contractId) {
        this.contractId = contractId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", tenantId=" + tenantId +
                ", amount=" + amount +
                ", dueDate='" + dueDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

package com.ams.controller;

import com.ams.config.ConnectionPool;
import com.ams.model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PaymentController - Handles payment management operations
 * Uses connection pooling for better performance and resource management
 */
public class PaymentController {

    private ConnectionPool connectionPool;

    public PaymentController() {
        try {
            this.connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }
    
    /**
     * Add new payment
     */
    public boolean addPayment(Payment payment) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "INSERT INTO payments (tenant_id, contract_id, amount, due_date, status, " +
                          "payment_method, receipt_number, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, payment.getTenantId());
            ps.setInt(2, payment.getContractId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getDueDate());
            ps.setString(5, payment.getStatus());
            ps.setString(6, payment.getPaymentMethod());
            ps.setString(7, payment.getReceiptNumber());
            ps.setString(8, payment.getNotes());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }
    
    /**
     * Update payment
     */
    public boolean updatePayment(Payment payment) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "UPDATE payments SET amount=?, due_date=?, status=?, " +
                          "payment_method=?, receipt_number=?, notes=? WHERE payment_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, payment.getAmount());
            ps.setString(2, payment.getDueDate());
            ps.setString(3, payment.getStatus());
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getReceiptNumber());
            ps.setString(6, payment.getNotes());
            ps.setInt(7, payment.getPaymentId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating payment: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }
    
    /**
     * Get all payments
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "SELECT * FROM payments ORDER BY due_date DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("tenant_id"),
                    rs.getInt("contract_id"),
                    rs.getDouble("amount"),
                    rs.getString("payment_date"),
                    rs.getString("due_date"),
                    rs.getString("status"),
                    rs.getString("payment_method"),
                    rs.getString("receipt_number")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payments: " + e.getMessage());
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return payments;
    }
    
    /**
     * Get payments by tenant
     */
    public List<Payment> getPaymentsByTenant(int tenantId) {
        List<Payment> payments = new ArrayList<>();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "SELECT * FROM payments WHERE tenant_id = ? ORDER BY due_date DESC";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, tenantId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("tenant_id"),
                    rs.getInt("contract_id"),
                    rs.getDouble("amount"),
                    rs.getString("payment_date"),
                    rs.getString("due_date"),
                    rs.getString("status"),
                    rs.getString("payment_method"),
                    rs.getString("receipt_number")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payments: " + e.getMessage());
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return payments;
    }
}

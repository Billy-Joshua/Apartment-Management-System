package com.ams.controller;

import com.ams.config.ConnectionPool;
import com.ams.model.Contract;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ContractController - Handles contract management operations
 * Uses connection pooling for better performance and resource management
 */
public class ContractController {

    private ConnectionPool connectionPool;

    public ContractController() {
        try {
            this.connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }
    
    /**
     * Add new contract
     */
    public boolean addContract(Contract contract) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "INSERT INTO contracts (tenant_id, room_id, start_date, end_date, " +
                          "monthly_rent, security_deposit, terms, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, contract.getTenantId());
            ps.setInt(2, contract.getRoomId());
            ps.setString(3, contract.getStartDate());
            ps.setString(4, contract.getEndDate());
            ps.setDouble(5, contract.getMonthlyRent());
            ps.setDouble(6, contract.getSecurityDeposit());
            ps.setString(7, contract.getTerms());
            ps.setString(8, contract.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding contract: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }
    
    /**
     * Get all contracts
     */
    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "SELECT * FROM contracts ORDER BY start_date DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                contracts.add(new Contract(
                    rs.getInt("contract_id"),
                    rs.getInt("tenant_id"),
                    rs.getInt("room_id"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getDouble("monthly_rent"),
                    rs.getDouble("security_deposit"),
                    rs.getString("terms"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching contracts: " + e.getMessage());
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return contracts;
    }
    
    /**
     * Get contract by tenant
     */
    public Contract getContractByTenant(int tenantId) {
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            String query = "SELECT * FROM contracts WHERE tenant_id = ? AND status = 'ACTIVE'";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, tenantId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Contract(
                    rs.getInt("contract_id"),
                    rs.getInt("tenant_id"),
                    rs.getInt("room_id"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getDouble("monthly_rent"),
                    rs.getDouble("security_deposit"),
                    rs.getString("terms"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching contract: " + e.getMessage());
        } finally {
            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
        return null;
    }
}

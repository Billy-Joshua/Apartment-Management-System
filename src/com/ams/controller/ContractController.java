package com.ams.controller;

import com.ams.config.DatabaseConfig;
import com.ams.model.Contract;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ContractController - Handles contract management operations
 */
public class ContractController {
    
    private Connection conn;
    
    public ContractController() {
        this.conn = DatabaseConfig.getConnection();
    }
    
    /**
     * Add new contract
     */
    public boolean addContract(Contract contract) {
        try {
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
        }
    }
    
    /**
     * Get all contracts
     */
    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        try {
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
        }
        return contracts;
    }
    
    /**
     * Get contract by tenant
     */
    public Contract getContractByTenant(int tenantId) {
        try {
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
        }
        return null;
    }
}

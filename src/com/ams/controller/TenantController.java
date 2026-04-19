package com.ams.controller;

import com.ams.config.DatabaseConfig;
import com.ams.model.Tenant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TenantController - Handles tenant management operations
 */
public class TenantController {
    
    private Connection conn;
    
    public TenantController() {
        this.conn = DatabaseConfig.getConnection();
    }
    
    /**
     * Add new tenant
     */
    public boolean addTenant(Tenant tenant) {
        try {
            if (tenant.getUserId() <= 0) {
                int userId = findOrCreateTenantUser(tenant);
                if (userId <= 0) {
                    throw new SQLException("Unable to create or locate tenant user account.");
                }
                tenant.setUserId(userId);
            }

            String query = "INSERT INTO tenants (user_id, first_name, last_name, email, phone, room_id, " +
                          "move_in_date, emergency_contact, emergency_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, tenant.getUserId());
            ps.setString(2, tenant.getFirstName());
            ps.setString(3, tenant.getLastName());
            ps.setString(4, tenant.getEmail());
            ps.setString(5, tenant.getPhone());
            ps.setInt(6, tenant.getRoomId());
            ps.setString(7, tenant.getMoveInDate());
            ps.setString(8, tenant.getEmergencyContact());
            ps.setString(9, tenant.getEmergencyPhone());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding tenant: " + e.getMessage());
            return false;
        }
    }

    private int findOrCreateTenantUser(Tenant tenant) throws SQLException {
        int existingUserId = getTenantUserIdByEmail(tenant.getEmail());
        if (existingUserId > 0) {
            return existingUserId;
        }

        String username = generateUsername(tenant);
        String password = "tenant123";
        String role = "TENANT";
        String status = "ACTIVE";

        String insertUserSql = "INSERT INTO users (username, password, email, role, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, tenant.getEmail());
        ps.setString(4, role);
        ps.setString(5, status);

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            return -1;
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }

        return -1;
    }

    private int getTenantUserIdByEmail(String email) throws SQLException {
        String query = "SELECT user_id FROM users WHERE email = ? AND role = 'TENANT'";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("user_id");
        }
        return -1;
    }

    private String generateUsername(Tenant tenant) throws SQLException {
        String baseUsername = "user";
        if (tenant.getEmail() != null && tenant.getEmail().contains("@")) {
            baseUsername = tenant.getEmail().split("@")[0];
        } else if (tenant.getFirstName() != null && tenant.getLastName() != null) {
            baseUsername = (tenant.getFirstName() + tenant.getLastName()).toLowerCase();
        }

        String username = baseUsername.replaceAll("[^a-zA-Z0-9]", "");
        if (username.isEmpty()) {
            username = "tenant" + System.currentTimeMillis();
        }

        int suffix = 0;
        while (usernameExists(username)) {
            suffix++;
            username = baseUsername + suffix;
        }

        return username;
    }

    private boolean usernameExists(String username) throws SQLException {
        String query = "SELECT user_id FROM users WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    /**
     * Update tenant
     */
    public boolean updateTenant(Tenant tenant) {
        try {
            String query = "UPDATE tenants SET first_name=?, last_name=?, email=?, phone=?, room_id=?, " +
                          "move_in_date=?, emergency_contact=?, emergency_phone=? WHERE tenant_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, tenant.getFirstName());
            ps.setString(2, tenant.getLastName());
            ps.setString(3, tenant.getEmail());
            ps.setString(4, tenant.getPhone());
            ps.setInt(5, tenant.getRoomId());
            ps.setString(6, tenant.getMoveInDate());
            ps.setString(7, tenant.getEmergencyContact());
            ps.setString(8, tenant.getEmergencyPhone());
            ps.setInt(9, tenant.getTenantId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating tenant: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete tenant with proper cascade handling
     */
    public boolean deleteTenant(int tenantId) {
        Connection conn = DatabaseConfig.getConnection();
        try {
            conn.setAutoCommit(false);
            
            // Step 1: Delete all payments related to this tenant's contracts
            String deletePaymentsQuery = "DELETE FROM payments WHERE contract_id IN " +
                    "(SELECT contract_id FROM contracts WHERE tenant_id = ?)";
            PreparedStatement ps1 = conn.prepareStatement(deletePaymentsQuery);
            ps1.setInt(1, tenantId);
            ps1.executeUpdate();
            
            // Step 2: Delete all contracts for this tenant
            String deleteContractsQuery = "DELETE FROM contracts WHERE tenant_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(deleteContractsQuery);
            ps2.setInt(1, tenantId);
            ps2.executeUpdate();
            
            // Step 3: Get the user_id before deleting the tenant
            String getUserIdQuery = "SELECT user_id FROM tenants WHERE tenant_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(getUserIdQuery);
            ps3.setInt(1, tenantId);
            ResultSet rs = ps3.executeQuery();
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
            
            // Step 4: Delete the tenant
            String deleteTenantQuery = "DELETE FROM tenants WHERE tenant_id = ?";
            PreparedStatement ps4 = conn.prepareStatement(deleteTenantQuery);
            ps4.setInt(1, tenantId);
            int result = ps4.executeUpdate();
            
            // Step 5: Delete the associated user (optional, based on business logic)
            if (userId > 0) {
                String deleteUserQuery = "DELETE FROM users WHERE user_id = ? AND role = 'TENANT'";
                PreparedStatement ps5 = conn.prepareStatement(deleteUserQuery);
                ps5.setInt(1, userId);
                ps5.executeUpdate();
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            return result > 0;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback error: " + rollbackEx.getMessage());
            }
            System.err.println("Error deleting tenant: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all tenants
     */
    public List<Tenant> getAllTenants() {
        List<Tenant> tenants = new ArrayList<>();
        try {
            String query = "SELECT * FROM tenants ORDER BY first_name";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                tenants.add(new Tenant(
                    rs.getInt("tenant_id"),
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getInt("room_id"),
                    rs.getString("move_in_date"),
                    rs.getString("emergency_contact"),
                    rs.getString("emergency_phone")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tenants: " + e.getMessage());
        }
        return tenants;
    }
    
    /**
     * Get tenant by ID
     */
    public Tenant getTenantById(int tenantId) {
        try {
            String query = "SELECT * FROM tenants WHERE tenant_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, tenantId);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Tenant(
                    rs.getInt("tenant_id"),
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getInt("room_id"),
                    rs.getString("move_in_date"),
                    rs.getString("emergency_contact"),
                    rs.getString("emergency_phone")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tenant: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get tenant by user ID
     */
    public Tenant getTenantByUserId(int userId) {
        try {
            String query = "SELECT * FROM tenants WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Tenant(
                    rs.getInt("tenant_id"),
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getInt("room_id"),
                    rs.getString("move_in_date"),
                    rs.getString("emergency_contact"),
                    rs.getString("emergency_phone")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tenant: " + e.getMessage());
        }
        return null;
    }
}

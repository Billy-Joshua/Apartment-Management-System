-- ============================================================================
-- APARTMENT MANAGEMENT SYSTEM - DATABASE SCHEMA
-- Database Name: apartment_management_system
-- ============================================================================

-- Create Database
CREATE DATABASE IF NOT EXISTS apartment_management_system;
USE apartment_management_system;

-- ============================================================================
-- TABLE 1: USERS (Authentication & Role Management)
-- ============================================================================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'TENANT') NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    INDEX idx_username (username),
    INDEX idx_role (role)
);

-- ============================================================================
-- TABLE 2: ROOMS (Apartment/Room Information)
-- ============================================================================
CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    floor INT NOT NULL,
    room_type VARCHAR(20),
    status ENUM('VACANT', 'OCCUPIED', 'MAINTENANCE') DEFAULT 'VACANT',
    monthly_rent DECIMAL(10, 2) NOT NULL,
    description TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (status),
    INDEX idx_room_number (room_number)
);

-- ============================================================================
-- TABLE 3: TENANTS (Tenant Information)
-- ============================================================================
CREATE TABLE tenants (
    tenant_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    room_id INT,
    move_in_date DATE,
    emergency_contact VARCHAR(50),
    emergency_phone VARCHAR(15),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_room_id (room_id),
    INDEX idx_email (email)
);

-- ============================================================================
-- TABLE 4: CONTRACTS (Lease Information)
-- ============================================================================
CREATE TABLE contracts (
    contract_id INT AUTO_INCREMENT PRIMARY KEY,
    tenant_id INT NOT NULL,
    room_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    monthly_rent DECIMAL(10, 2) NOT NULL,
    security_deposit DECIMAL(10, 2),
    terms TEXT,
    status ENUM('ACTIVE', 'EXPIRED', 'TERMINATED') DEFAULT 'ACTIVE',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE RESTRICT,
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_status (status)
);

-- ============================================================================
-- TABLE 5: PAYMENTS (Rent Payment Tracking)
-- ============================================================================
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    tenant_id INT NOT NULL,
    contract_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date DATE NOT NULL,
    status ENUM('PAID', 'PENDING', 'OVERDUE') DEFAULT 'PENDING',
    payment_method VARCHAR(20),
    receipt_number VARCHAR(50),
    notes TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id) ON DELETE CASCADE,
    FOREIGN KEY (contract_id) REFERENCES contracts(contract_id) ON DELETE RESTRICT,
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_status (status),
    INDEX idx_due_date (due_date)
);

-- ============================================================================
-- SAMPLE DATA FOR TESTING
-- ============================================================================

-- Insert Admin User
INSERT INTO users (username, password, email, role, status) 
VALUES ('admin', 'admin123', 'admin@ams.com', 'ADMIN', 'ACTIVE');

-- Insert Manager Users
INSERT INTO users (username, password, email, role, status) 
VALUES 
('manager1', 'manager123', 'manager1@ams.com', 'MANAGER', 'ACTIVE'),
('manager2', 'manager123', 'manager2@ams.com', 'MANAGER', 'ACTIVE');

-- Insert Tenant Users
INSERT INTO users (username, password, email, role, status) 
VALUES 
('tenant1', 'tenant123', 'tenant1@ams.com', 'TENANT', 'ACTIVE'),
('tenant2', 'tenant123', 'tenant2@ams.com', 'TENANT', 'ACTIVE'),
('tenant3', 'tenant123', 'tenant3@ams.com', 'TENANT', 'ACTIVE');

-- Insert Rooms
INSERT INTO rooms (room_number, floor, room_type, status, monthly_rent, description) 
VALUES 
('101', 1, '1BHK', 'VACANT', 15000, '1 Bedroom, Hall, Kitchen'),
('102', 1, '2BHK', 'OCCUPIED', 20000, '2 Bedrooms, Hall, Kitchen'),
('201', 2, '1BHK', 'OCCUPIED', 15000, '1 Bedroom, Hall, Kitchen'),
('202', 2, '2BHK', 'VACANT', 20000, '2 Bedrooms, Hall, Kitchen'),
('301', 3, '1BHK', 'MAINTENANCE', 15000, '1 Bedroom, Hall, Kitchen'),
('302', 3, '3BHK', 'VACANT', 25000, '3 Bedrooms, Hall, Kitchen, Balcony');

-- Insert Tenants
INSERT INTO tenants (user_id, first_name, last_name, email, phone, room_id, move_in_date, emergency_contact, emergency_phone) 
VALUES 
(4, 'Raj', 'Kumar', 'tenant1@ams.com', '9876543210', 2, '2024-01-15', 'Priya Kumar', '9876543211'),
(5, 'Priya', 'Singh', 'tenant2@ams.com', '9876543212', 3, '2024-02-20', 'Rajesh Singh', '9876543213'),
(6, 'Amit', 'Patel', 'tenant3@ams.com', '9876543214', NULL, NULL, 'Neha Patel', '9876543215');

-- Insert Contracts
INSERT INTO contracts (tenant_id, room_id, start_date, end_date, monthly_rent, security_deposit, terms, status) 
VALUES 
(1, 2, '2024-01-15', '2025-01-14', 20000, 40000, 'Monthly lease agreement', 'ACTIVE'),
(2, 3, '2024-02-20', '2025-02-19', 15000, 30000, 'Monthly lease agreement', 'ACTIVE'),
(3, 1, '2024-03-01', '2025-02-28', 15000, 30000, 'Monthly lease agreement', 'ACTIVE');

-- Insert Payments
INSERT INTO payments (tenant_id, contract_id, amount, due_date, status, payment_method, receipt_number) 
VALUES 
(1, 1, 20000, '2024-02-15', 'PAID', 'BANK_TRANSFER', 'REC001'),
(1, 1, 20000, '2024-03-15', 'PAID', 'BANK_TRANSFER', 'REC002'),
(1, 1, 20000, '2024-04-15', 'PENDING', 'BANK_TRANSFER', NULL),
(2, 2, 15000, '2024-03-20', 'PAID', 'CASH', 'REC003'),
(2, 2, 15000, '2024-04-20', 'OVERDUE', 'BANK_TRANSFER', NULL),
(3, 3, 15000, '2024-04-01', 'PENDING', 'BANK_TRANSFER', NULL);

-- ============================================================================
-- SUMMARY
-- ============================================================================
-- Total Tables: 5 (users, rooms, tenants, contracts, payments)
-- Sample Data:
--   - 1 Admin user
--   - 2 Manager users
--   - 3 Tenant users
--   - 6 Rooms
--   - 3 Tenants assigned to rooms
--   - 3 Contracts
--   - 6 Payment records
-- ============================================================================

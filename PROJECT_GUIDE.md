# APARTMENT MANAGEMENT SYSTEM
## Complete Professional Guide for University Project

---

## TABLE OF CONTENTS
1. Project Overview
2. User Roles & Permissions
3. Functional Requirements
4. Database Design
5. Project Structure
6. Setup Instructions
7. How to Use Each Module
8. Presentation Script

---

## 1. PROJECT OVERVIEW

**System Name:** Apartment Management System
**Technology Stack:** 
- Language: Java (JDK 8 or higher)
- Database: MySQL 5.7+ (via XAMPP)
- GUI: Java Swing
- Architecture: Model-View-Controller (MVC)
- Networking: Socket Programming (for multiple users)

**Core Purpose:**
Manage apartment buildings efficiently by tracking tenants, rooms, rent payments, and contracts.

---

## 2. USER ROLES & PERMISSIONS

### **Admin Role**
**What Admin Can Do:**
- ✅ View all tenants
- ✅ Add/Edit/Delete tenants
- ✅ Manage all rooms and their status
- ✅ View payment history
- ✅ Send payment reminders
- ✅ Generate reports
- ✅ Manage managers (add/remove)
- ✅ Full system access

**Dashboard Features:**
- Summary statistics
- All users list
- All payments list
- System settings

---

### **Manager Role**
**What Manager Can Do:**
- ✅ View assigned tenants
- ✅ Add new tenants (requires Admin approval)
- ✅ Check available rooms
- ✅ View payment status
- ✅ Send reminders to tenants
- ✅ Upload contracts
- ❌ Cannot delete tenants
- ❌ Cannot access admin settings

**Dashboard Features:**
- Assigned tenants only
- Available rooms
- Pending payments
- My contracts

---

### **Tenant Role**
**What Tenant Can Do:**
- ✅ View own profile
- ✅ View own payment history
- ✅ View room details
- ✅ Check contract terms
- ✅ Submit maintenance requests
- ❌ Cannot add/edit other tenants
- ❌ Cannot manage payments themselves
- ❌ Cannot access admin functions

**Dashboard Features:**
- Personal information
- Payment history
- Room details
- Contract view

---

## 3. FUNCTIONAL REQUIREMENTS

### **Requirement 1: User Management**
- [x] Users can register (manager/tenant registration with admin approval)
- [x] Secure login with role-based authentication
- [x] Password storage (consider hashing in production)
- [x] User profile management

### **Requirement 2: Tenant Management**
- [x] Add new tenant (Manager/Admin)
- [x] View all tenants (filtered by role)
- [x] Edit tenant information
- [x] Delete tenant record (Admin only)
- [x] Search tenants by name/email
- [x] Track tenant contact information

### **Requirement 3: Room Management**
- [x] Add new rooms/apartments
- [x] Track room status (vacant, occupied, maintenance)
- [x] Assign rooms to tenants
- [x] View room details
- [x] Update room information

### **Requirement 4: Rent Payment System**
- [x] Record payment entries
- [x] Track payment status (paid, pending, overdue)
- [x] Calculate payment due dates
- [x] Generate payment receipts
- [x] Track payment history per tenant

### **Requirement 5: Contract Management**
- [x] Store contract details
- [x] Track contract dates (start, end)
- [x] Show contract terms
- [x] Auto-renewal reminders

### **Requirement 6: Notification System**
- [x] Send payment reminders
- [x] Contract expiry alerts
- [x] Maintenance request notifications

### **Requirement 7: Report Generation**
- [x] View payment reports
- [x] Tenant statistics
- [x] Occupancy reports

---

## 4. DATABASE DESIGN

### **Table 1: Users**
```
Stores login credentials for all users
Columns: user_id, username, password, email, role, created_date
```

### **Table 2: Tenants**
```
Stores tenant information
Columns: tenant_id, name, email, phone, room_id, move_in_date, contract_id
```

### **Table 3: Rooms**
```
Stores apartment/room information
Columns: room_id, room_number, floor, status (occupied/vacant/maintenance), rent_amount
```

### **Table 4: Payments**
```
Tracks rent payments
Columns: payment_id, tenant_id, amount, payment_date, due_date, status
```

### **Table 5: Contracts** (Bonus)
```
Stores tenant contracts
Columns: contract_id, tenant_id, start_date, end_date, terms, monthly_rent
```

---

## 5. PROJECT STRUCTURE (MVC ARCHITECTURE)

```
ApartmentManagementSystem/
├── src/
│   ├── com/
│   │   └── ams/
│   │       ├── Main.java                    # Entry point
│   │       ├── config/
│   │       │   └── DatabaseConfig.java     # DB connection
│   │       ├── model/
│   │       │   ├── User.java
│   │       │   ├── Tenant.java
│   │       │   ├── Room.java
│   │       │   ├── Payment.java
│   │       │   └── Contract.java
│   │       ├── controller/
│   │       │   ├── AuthController.java
│   │       │   ├── TenantController.java
│   │       │   ├── RoomController.java
│   │       │   ├── PaymentController.java
│   │       │   └── ContractController.java
│   │       ├── view/
│   │       │   ├── LoginFrame.java
│   │       │   ├── AdminDashboard.java
│   │       │   ├── ManagerDashboard.java
│   │       │   ├── TenantDashboard.java
│   │       │   ├── TenantManagementUI.java
│   │       │   ├── RoomManagementUI.java
│   │       │   ├── PaymentManagementUI.java
│   │       │   └── ContractManagementUI.java
│   │       ├── socket/
│   │       │   ├── Server.java
│   │       │   └── ClientHandler.java
│   │       └── utils/
│   │           ├── ValidationUtils.java
│   │           ├── DateUtils.java
│   │           └── Constants.java
│   └── database/
│       └── database_schema.sql              # SQL scripts
│
├── lib/                                     # External JARs (MySQL connector)
│
├── DATABASE_SETUP.md                        # Setup instructions
├── PROJECT_GUIDE.md                         # This file
└── README.md                                # Quick start guide
```

---

## 6. SETUP INSTRUCTIONS

### **Step 1: Install XAMPP**
1. Download XAMPP from https://www.apachefriends.org/
2. Install it (default path: C:\xampp)
3. Start Apache & MySQL from XAMPP Control Panel

### **Step 2: Create Database**
1. Open phpMyAdmin: http://localhost/phpmyadmin
2. Create new database named: `apartment_management_system`
3. Execute the SQL scripts (see database_schema.sql)

### **Step 3: Configure Java Project**
1. Add MySQL JDBC driver to `lib/` folder:
   - Download mysql-connector-java-8.0.jar
   - Place in project lib folder
2. Update DatabaseConfig.java with your credentials

### **Step 4: Run the Project**
1. Compile: `javac src/com/ams/*.java`
2. Run: `java -cp src:lib/* com.ams.Main`

---

## 7. HOW TO USE EACH MODULE

### **A. Login System**
- User enters username and password
- System queries Users table
- Checks password and role
- Redirects to appropriate dashboard

### **B. Admin Dashboard**
- Overview of all statistics
- Links to manage tenants, rooms, payments
- Access to admin settings

### **C. Manager Dashboard**
- Overview of assigned tenants
- Available rooms display
- Payment tracking

### **D. Tenant Dashboard**
- View personal information
- Check payment history
- View contract details

### **E. CRUD Operations**
- **Create:** Add new record via form
- **Read:** Display records in JTable
- **Update:** Edit existing record
- **Delete:** Remove record (with confirmation)

### **F. Socket Programming**
- Server accepts multiple client connections
- Each client runs in separate thread
- Clients can request data/perform operations
- Server handles requests and sends responses

---

## 8. PRESENTATION SCRIPT (2-3 Minutes)

### **Script for Presentation:**

---

**"Good morning/afternoon everyone. I have built an Apartment Management System using Java, MySQL, and Swing.**

**Let me walk you through what this system does:**

**SLIDE 1 - System Overview**
"This system manages the complete lifecycle of apartment management. It has three main user types: Admins who control everything, Managers who handle tenants, and Tenants who manage their own accounts."

**SLIDE 2 - Database Architecture**
"The system uses 5 interrelated tables: Users for authentication, Tenants for resident data, Rooms for apartment inventory, Payments for tracking rent, and Contracts for lease agreements. Each table is connected through foreign keys to maintain data integrity."

**SLIDE 3 - Login Demonstration**
"Let me show you the login system. Users enter their credentials, and based on their role, they're directed to their specific dashboard. This is role-based access control in action."

**SLIDE 4 - Admin Dashboard**
"As an Admin, I can see the complete system overview. I can manage tenants, rooms, payments, and even manage other managers. Notice the summary statistics at the top."

**SLIDE 5 - CRUD Operations**
"The system implements all CRUD operations. I can Create new tenants, Read existing ones in this table, Update their information, and Delete records with proper confirmation."

**SLIDE 6 - MVC Architecture**
"Behind the scenes, I've implemented a clean MVC architecture. Models handle data, Views display the UI, and Controllers process user interactions and business logic. This separation makes the code maintainable and scalable."

**SLIDE 7 - Socket Programming**
"For multiple simultaneous users, I've implemented socket programming. The server handles multiple client connections, each client runs in its own thread, allowing the system to serve many users at once."

**SLIDE 8 - Features Summary**
"The system tracks payments, manages contracts, shows available rooms, and can send automated reminders. All data is securely stored in MySQL database."

**SLIDE 9 - Conclusion**
"This system demonstrates key software engineering concepts: user authentication, database design, object-oriented programming, GUI development, and network programming. Thank you!""

---

That's it! This gives a complete, impressive presentation. Adjust based on your actual implementation details.

---

## NEXT STEPS

1. **Setup Database** → Run database_schema.sql
2. **Configure Connection** → Update DatabaseConfig.java
3. **Run Main.java** → Start the system
4. **Test Login** → Use provided credentials
5. **Explore Features** → Test each role's functionality
6. **Demo & Present** → Use this guide for presentation

---

**Good Luck with your project! 🎓**

# APARTMENT MANAGEMENT SYSTEM
## Complete Setup & Deployment Guide

---

## TABLE OF CONTENTS
1. Pre-Installation Requirements
2. Install XAMPP & MySQL
3. Create Database
4. Configure Java Project
5. Run the Application
6. Testing Instructions
7. Troubleshooting

---

## 1. PRE-INSTALLATION REQUIREMENTS

### **System Requirements**
- Windows 7 or later (or Linux/Mac)
- At least 2GB RAM
- 100MB free disk space
- Internet connection (for downloads)

### **Software Required**
- **Java Development Kit (JDK) 8 or higher**
  - Download: https://www.oracle.com/java/technologies/javase-jdk15-downloads.html
  - Or use OpenJDK
  
- **XAMPP (Apache, MySQL, PHP)**
  - Download: https://www.apachefriends.org/
  - Version: 7.4 or higher
  
- **MySQL Connector Java**
  - Download: https://dev.mysql.com/downloads/connector/j/
  - Version: 8.0.x
  
- **Text Editor / IDE**
  - VS Code, NetBeans, IntelliJ IDEA, or Eclipse

---

## 2. INSTALL XAMPP & MYSQL

### **Step 2.1: Download XAMPP**
1. Go to https://www.apachefriends.org/
2. Download version for your OS (Windows/Linux/Mac)
3. Version 7.4.x recommended

### **Step 2.2: Install XAMPP**
1. Run the installer
2. Choose installation directory (default: C:\xampp)
3. Uncheck "Apache" if you don't need web server
4. Check "MySQL" and "PHP" (PHP optional)
5. Click Install

### **Step 2.3: Start MySQL Service**
1. Open XAMPP Control Panel
2. Click "Start" button next to MySQL
3. Status should show as "Running" (green)
4. Note: Keep XAMPP running while using the application

---

## 3. CREATE DATABASE

### **Step 3.1: Open phpMyAdmin**
1. From XAMPP Control Panel, click "Admin" button next to MySQL
2. Or open browser: http://localhost/phpmyadmin
3. You should see the MySQL administration interface

### **Step 3.2: Create Database**
1. Click on "Databases" tab
2. In "Create database" section:
   - Database name: `apartment_management_system`
   - Collation: utf8_general_ci
3. Click "Create"

### **Step 3.3: Run SQL Script**
1. Select the `apartment_management_system` database
2. Click "Import" tab
3. Choose the `database_schema.sql` file from your project
4. Click "Go" to execute
5. All 5 tables will be created automatically

### **Step 3.4: Verify Tables**
1. In phpMyAdmin, go to the database
2. You should see these 5 tables:
   - users
   - rooms
   - tenants
   - contracts
   - payments
3. Click on each to verify structure

### **Sample Data Already Included**
The database_schema.sql includes sample data:
- 1 Admin user
- 2 Manager users
- 3 Tenant users
- 6 Sample rooms
- 3 Sample contracts
- 6 Sample payments

---

## 4. CONFIGURE JAVA PROJECT

### **Step 4.1: Add MySQL Connector**
1. Download `mysql-connector-java-8.0.x.jar`
2. Copy to: `ApartmentManagementSystem/lib/` folder
3. If `lib` folder doesn't exist, create it

### **Step 4.2: Verify Database Credentials**
1. Open file: `src/com/ams/utils/Constants.java`
2. Check these settings:
```java
public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
public static final String DB_URL = "jdbc:mysql://localhost:3306/apartment_management_system";
public static final String DB_USER = "root";
public static final String DB_PASSWORD = "";
```

3. If your MySQL password is different, update `DB_PASSWORD`
4. If MySQL is on different host, update `DB_URL`

### **Step 4.3: Compile Java Code**
Option A - Using Command Line:
```bash
cd "d:\Apartment Management System"
javac -cp src:lib/* src/com/ams/model/*.java
javac -cp src:lib/* src/com/ams/config/*.java
javac -cp src:lib/* src/com/ams/controller/*.java
javac -cp src:lib/* src/com/ams/view/*.java
javac -cp src:lib/* src/com/ams/utils/*.java
javac -cp src:lib/* src/com/ams/socket/*.java
javac -cp src:lib/* src/com/ams/*.java
```

Option B - Using IDE:
- If using NetBeans/IntelliJ:
  1. Add the project
  2. Set JDK 8 or higher
  3. Add MySQL JAR to libraries
  4. Build project

---

## 5. RUN THE APPLICATION

### **Method 1: Command Line**
```bash
cd "d:\Apartment Management System"
java -cp src:lib/* com.ams.Main
```

### **Method 2: Using IDE**
- Right-click on Main.java
- Select "Run" or "Run As Java Application"

### **Method 3: Create Batch File (Windows)**
Create a file `run.bat`:
```batch
@echo off
cd /d "d:\Apartment Management System"
java -cp src:lib/* com.ams.Main
pause
```
Then double-click `run.bat` to run.

### **Expected Output**
```
==================================================
APARTMENT MANAGEMENT SYSTEM - INITIALIZATION
==================================================

[INIT] Testing database connection...
[DATABASE] Connected successfully!
[INIT] Database connection successful!

[INIT] Launching application...

[OK] Application started successfully!
==================================================
```

Then the Login Frame should appear.

---

## 6. TESTING INSTRUCTIONS

### **Login Credentials**

**Admin Account:**
- Username: `admin`
- Password: `admin123`
- Role: Admin

**Manager Accounts:**
- Username: `manager1`
- Password: `manager123`
- Role: Manager

**Tenant Accounts:**
- Username: `tenant1`
- Password: `tenant123`
- Role: Tenant

### **Test Cases**

#### **Test 1: Admin Login & Dashboard**
1. Run application
2. Enter admin credentials
3. Verify Admin Dashboard opens
4. Check statistics (Total Tenants, Total Rooms, etc.)

#### **Test 2: Tenant Management**
1. Login as Admin
2. Go to "Tenants" tab
3. Click "Refresh" to see existing tenants
4. Try adding new tenant (fill all fields)
5. Verify in table
6. Try updating a tenant
7. Try deleting a tenant

#### **Test 3: Room Management**
1. Login as Admin
2. Go to "Rooms" tab
3. Add new room with details
4. Verify status (VACANT/OCCUPIED/MAINTENANCE)
5. Update room rent
6. Delete room

#### **Test 4: Payment Management**
1. Login as Admin
2. Go to "Payments" tab
3. View all payments
4. Check different payment statuses
5. Try "Mark as Paid" feature

#### **Test 5: Contract Management**
1. Login as Admin
2. Go to "Contracts" tab
3. View all contracts
4. Check contract dates and terms

#### **Test 6: Manager Login**
1. Logout from Admin
2. Login as Manager
3. Verify limited access (only assigned tenants/rooms)

#### **Test 7: Tenant Login**
1. Logout from Manager
2. Login as Tenant
3. View personal information
4. View payment history
5. View contract details

#### **Test 8: Data Persistence**
1. Add new data
2. Close application
3. Reopen application
4. Verify data is still saved in database

---

## 7. TROUBLESHOOTING

### **Issue 1: "Connection refused" Error**
**Cause:** MySQL is not running
**Solution:**
1. Open XAMPP Control Panel
2. Start MySQL service
3. Restart application

### **Issue 2: "Unknown database" Error**
**Cause:** Database not created
**Solution:**
1. Open phpMyAdmin
2. Create database `apartment_management_system`
3. Import `database_schema.sql`

### **Issue 3: "ClassNotFoundException: com.mysql.jdbc.Driver"**
**Cause:** MySQL JAR not in classpath
**Solution:**
1. Download mysql-connector-java-8.0.x.jar
2. Place in `lib/` folder
3. Ensure it's added to classpath when compiling/running

### **Issue 4: Login fails with correct credentials**
**Cause:** Database has no users or incorrect credentials
**Solution:**
1. Verify database_schema.sql was executed
2. Check if sample data was inserted
3. Use phpMyAdmin to verify users table

### **Issue 5: Application crashes after login**
**Cause:** Controllers or database connection issue
**Solution:**
1. Check database connection
2. Verify all tables exist
3. Check console for error messages
4. Ensure MySQL is running

### **Issue 6: "Port already in use" (for Socket Server)**
**Cause:** Another application using port 5555
**Solution:**
1. Change port in Constants.java (SOCKET_PORT)
2. Or close the application using that port

### **Issue 7: Tables not showing data**
**Cause:** Data not properly inserted
**Solution:**
1. Check phpMyAdmin > Select database > View table data
2. If empty, run database_schema.sql again
3. Or manually insert test data

---

## NEXT STEPS

After setup is complete:

1. **Explore Features**
   - Test each role's functionality
   - Try all CRUD operations
   - Check role-based access control

2. **Customize**
   - Modify colors and themes in View classes
   - Add new fields/validations
   - Create additional reports

3. **Deploy**
   - Package as JAR file
   - Create installer using NSIS or WIX
   - Deploy to network (as mentioned in socket programming)

4. **Enhance**
   - Add more complex queries
   - Implement PDF report generation
   - Add email notifications
   - Implement user audit trails

---

## SUPPORT & DOCUMENTATION

- Database Schema: See `database_schema.sql`
- Project Structure: See `PROJECT_GUIDE.md`
- API Documentation: See individual classes (JavaDoc comments)
- For issues: Check console output and database logs

---

**Good Luck with your Project!** 🎓

For any questions during implementation, refer to the PROJECT_GUIDE.md for detailed explanations of each component.

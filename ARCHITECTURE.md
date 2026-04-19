# ARCHITECTURE DOCUMENTATION
## Apartment Management System - MVC Implementation

---

## 1. SYSTEM ARCHITECTURE OVERVIEW

```
┌─────────────────────────────────────────────────────┐
│           APARTMENT MANAGEMENT SYSTEM              │
└─────────────────────────────────────────────────────┘

        ┌──────────────┐
        │    VIEW      │
        │  (GUI Forms) │
        └──────┬───────┘
               │
        ┌──────▼───────┐
        │ CONTROLLER   │
        │(Business     │
        │  Logic)      │
        └──────┬───────┘
               │
        ┌──────▼───────┐
        │    MODEL     │
        │  (Database   │
        │   Access)    │
        └──────┬───────┘
               │
        ┌──────▼───────┐
        │   DATABASE   │
        │   (MySQL)    │
        └──────────────┘
```

---

## 2. MVC COMPONENT DETAILS

### **MODEL LAYER** (Data & Database Access)
Location: `src/com/ams/model/`  
Responsibility: Data structure and persistence

**Classes:**
- `User.java` - User entity (id, username, password, role)
- `Tenant.java` - Tenant entity (name, email, phone, roomId)
- `Room.java` - Room entity (roomNumber, floor, status, rent)
- `Payment.java` - Payment entity (amount, dueDate, status)
- `Contract.java` - Contract entity (startDate, endDate, rent)

**Key Characteristics:**
- Implements Serializable for network communication
- No business logic - pure data containers
- Getters and setters for all properties
- toString() methods for debugging

### **CONTROLLER LAYER** (Business Logic)
Location: `src/com/ams/controller/`  
Responsibility: CRUD operations and data validation

**Classes:**
- `AuthController.java` - Login, registration, authentication
- `TenantController.java` - Tenant CRUD operations
- `RoomController.java` - Room CRUD operations
- `PaymentController.java` - Payment CRUD operations
- `ContractController.java` - Contract CRUD operations

**Key Methods:**
```
Add    → INSERT into database
Read   → SELECT from database (single/all)
Update → UPDATE database records
Delete → DELETE from database
```

**Example: TenantController**
```java
// Create
public boolean addTenant(Tenant tenant)

// Read
public List<Tenant> getAllTenants()
public Tenant getTenantById(int tenantId)

// Update
public boolean updateTenant(Tenant tenant)

// Delete
public boolean deleteTenant(int tenantId)
```

### **VIEW LAYER** (User Interface)
Location: `src/com/ams/view/`  
Responsibility: UI presentation and user interaction

**Classes:**
- `LoginFrame.java` - Login screen with role selection
- `AdminDashboard.java` - Admin's main dashboard
- `ManagerDashboard.java` - Manager's main dashboard
- `TenantDashboard.java` - Tenant's main dashboard
- `TenantManagementUI.java` - Tenant CRUD forms
- `RoomManagementUI.java` - Room CRUD forms
- `PaymentManagementUI.java` - Payment viewing/updating
- `ContractManagementUI.java` - Contract viewing

**Data Flow in Views:**
1. User enters data in form
2. Form validates input (using ValidationUtils)
3. Calls appropriate Controller method
4. Controller accesses Model/Database
5. View displays result (table, message dialog)

---

## 3. COMPLETE DATA FLOW EXAMPLE

### **Scenario: Admin Adds New Tenant**

```
User Interface          Controller           Model          Database
     │                      │                 │                │
     │ 1. Click "Save"      │                 │                │
     ├─────────────────────>│                 │                │
     │                      │ 2. Validate     │                │
     │                      │ 3. Create       │                │
     │                      │ Tenant object   │                │
     │                      ├────────────────>│                │
     │                      │                 │ 4. Execute SQL │
     │                      │                 ├───────────────>│
     │                      │                 │ INSERT query   │
     │                      │                 │                │
     │                      │                 │ 5. Success ✓   │
     │                      │                 │<───────────────┤
     │                      │<────────────────┤                │
     │                      │ return true     │                │
     │ 6. Show message      │                 │                │
     │ success + refresh    │                 │                │
     │<─────────────────────┤                 │                │
     │ 7. Refresh table     │                 │                │
     ├─────────────────────>│                 │                │
     │                      │ 8. Query all    │                │
     │                      │ tenants         │                │
     │                      ├────────────────>│                │
     │                      │                 │ 9. SELECT *    │
     │                      │                 ├───────────────>│
     │                      │                 │<───────────────┤
     │                      │                 │ 10. Return all │
     │                      │                 │ tenants        │
     │                      │<────────────────┤                │
     │ 11. Display in table │                 │                │
     │<─────────────────────┤                 │                │
     │                      │                 │                │
```

---

## 4. DATABASE RELATIONSHIPS

```
┌──────────────┐
│    USERS     │
├──────────────┤
│ user_id      │ ◄─────────────┐
│ username     │               │ (FK)
│ password     │               │
│ email        │               │
│ role         │               │
│ status       │               │
└──────────────┘               │
                               │
                               │
        ┌───────────────────┤  │
        │                   │  │
   ┌────▼────────┐    ┌────▼─────────┐
   │   TENANTS   │    │   MANAGERS    │
   ├─────────────┤    └───────────────┘
   │ tenant_id   │
   │ user_id (FK)├───────────┐
   │ room_id(FK) ├─────┐     │
   │ name        │     │     │
   │ email       │     │     │
   │ phone       │     │     │
   └─────────────┘     │     │
                       │     │
              ┌────────▼─┐   │
              │  ROOMS   │   │
              ├──────────┤   │
              │ room_id  │   │
              │ number   │   │
              │ floor    │   │
              │ status   │   │
              │ rent     │   │
              └──────────┘   │
                             │
              ┌──────────────┘
              │
         ┌────▼──────────┐
         │  CONTRACTS    │
         ├───────────────┤
         │ contract_id   │
         │ tenant_id(FK) │
         │ room_id(FK)   │
         │ start_date    │
         │ end_date      │
         │ rent          │
         └───────────────┘
                 │
                 │ (1 to many)
                 │
         ┌───────▼────────┐
         │   PAYMENTS     │
         ├────────────────┤
         │ payment_id     │
         │ tenant_id(FK)  │
         │ contract_id(FK)│
         │ amount         │
         │ due_date       │
         │ status         │
         └────────────────┘
```

---

## 5. ROLE-BASED ACCESS CONTROL

### **Admin**
```
LoginFrame (can login as admin)
    ↓
AdminDashboard
    ├─ Dashboard (statistics)
    ├─ Tenants (full CRUD)
    ├─ Rooms (full CRUD)
    ├─ Payments (view, update status)
    └─ Contracts (view, manage)
```

### **Manager**
```
LoginFrame (can login as manager)
    ↓
ManagerDashboard
    ├─ Tenants (assigned only)
    ├─ Available Rooms (view only)
    └─ Payments (view assigned)
```

### **Tenant**
```
LoginFrame (can login as tenant)
    ↓
TenantDashboard
    ├─ Profile (view own info)
    ├─ Payments (view own)
    └─ Contract (view own)
```

---

## 6. CONFIGURATION & UTILITIES

### **DatabaseConfig.java**
```java
// Manages database connections
- getConnection()  → Opens MySQL connection
- testConnection()  → Verifies connection
- closeConnection() → Closes connection
- Uses singleton pattern
```

### **Constants.java**
```java
// Application-wide constants
- DB_URL, DB_USER, DB_PASSWORD
- ROLE_ADMIN, ROLE_MANAGER, ROLE_TENANT
- ROOM_VACANT, ROOM_OCCUPIED, ROOM_MAINTENANCE
- PAYMENT_PAID, PAYMENT_PENDING, PAYMENT_OVERDUE
- WINDOW sizes, SOCKET port
```

### **ValidationUtils.java**
```java
// Input validation methods
- isEmpty()        → Checks for empty strings
- isValidEmail()   → Email format validation
- isValidPhone()   → Phone number validation
- isValidAmount()  → Currency amount validation
- isValidPassword() → Strong password check
- isValidDate()    → Date format validation
```

### **DateUtils.java**
```java
// Date/time utilities
- dateToSQL()      → Format for database
- dateToDisplay()  → Format for UI
- addDays()        → Add days to date
- addMonths()      → Add months to date
- getDaysDifference() → Calculate difference
```

---

## 7. SOCKET PROGRAMMING ARCHITECTURE

### **Server.java** (Multi-threaded)
```
Server (Main Thread)
    ├─ Accepts connection from Client 1 → Starts Thread 1
    ├─ Accepts connection from Client 2 → Starts Thread 2
    ├─ Accepts connection from Client 3 → Starts Thread 3
    └─ Continues accepting connections...

Each thread runs ClientHandler
```

### **ClientHandler.java** (Individual Thread)
```
ClientHandler (Thread for each client)
    ├─ Receive request from client
    ├─ Process request (GET_TENANTS, ADD_TENANT, etc.)
    ├─ Access database if needed
    ├─ Send response back to client
    └─ Repeat until client disconnects
```

### **Data Flow with Sockets**
```
Client 1          Socket Server        Client 2
   │                  │                   │
   ├─ CONNECT ──────>│                   │
   │                  ├─ START THREAD 1   │
   │                  │                   │
   │                  │                  ├─ CONNECT ──────>
   │                  │                  │   ├─ START THREAD 2
   ├─ REQUEST ──────>│                   │
   │                  ├─ THREAD 1:        │
   │                  │   Process        ├─ REQUEST ──────>
   │                  │   RESPONSE ────>│   THREAD 2:
   │<─ RESPONSE ─────┤                   │   Process
   │                  │                   │
   │                  │                  │<─ RESPONSE ─────┤
   │                  │                   │
   │                  │                   │
```

---

## 8. HOW TO ADD NEW FEATURES

### **Example: Add "Send Reminder" Feature**

**Step 1: Add to Model** (if needed)
```java
// RemindersController.java
public boolean sendReminder(int tenantId, String message)
```

**Step 2: Add Controller Logic**
```java
public class RemindersController {
    public boolean sendReminder(int tenantId, String message) {
        // Query tenant -> get email
        // Send email using JavaMail
        // Log to database
        return success;
    }
}
```

**Step 3: Add UI Button**
```java
// In PaymentManagementUI.java
JButton sendReminderButton = new JButton("Send Reminder");
sendReminderButton.addActionListener(e -> {
    RemindersController rc = new RemindersController();
    rc.sendReminder(tenantId, message);
});
```

**Step 4: Test**
- Test with different scenarios
- Check database logs
- Verify email delivery

---

## 9. CODE ORGANIZATION PRINCIPLES

✅ **Separation of Concerns**
- Model handles data
- Controller handles logic
- View handles presentation
- No mixing of responsibilities

✅ **Single Responsibility**
- Each class does one thing well
- Each method does one thing

✅ **DRY (Don't Repeat Yourself)**
- Common code in utilities
- Reusable components
- Constants centralized

✅ **Error Handling**
- Try-catch blocks
- User-friendly error messages
- Logging for debugging

---

## 10. EXTENSION POSSIBILITIES

1. **Email Notifications**
   - Send payment reminders
   - Alert for contract expiry
   
2. **PDF Reports**
   - Payment receipts
   - Tenant statements
   - Occupancy reports

3. **Advanced Search**
   - Filter by date range
   - Search by multiple criteria

4. **Reports & Analytics**
   - Revenue charts
   - Occupancy trends
   - Tenant demographics

5. **Enhanced Security**
   - Password encryption (MD5/SHA-256)
   - User audit trails
   - Activity logging

---

## 11. DEPLOYMENT ARCHITECTURE

```
Development Machine
    ↓
Java Code (src/)
    ↓
Compilation
    ↓
JAR File Creation
    ↓
Distribution
    ↓
Client Machines
    ├─ Install JRE (Java Runtime)
    ├─ Install MySQL
    ├─ Create database
    ├─ Run JAR file
    └─ Login with credentials
```

---

## SUMMARY: MVC IN ACTION

| Component | File | Responsibility |
|-----------|------|-----------------|
| **Model** | `model/*.java` | Data structure (User, Tenant, etc.) |
| **View** | `view/*.java` | UI forms (LoginFrame, Dashboards) |
| **Controller** | `controller/*.java` | CRUD logic (AuthController, etc.) |
| **Database** | MySQL | Persistent data storage |
| **Config** | `config/DatabaseConfig.java` | Connection management |
| **Utils** | `utils/*.java` | Validation, date, constants |
| **Socket** | `socket/*.java` | Multi-user server architecture |

---

This architecture ensures your code is:
- **Maintainable** - Easy to understand and modify
- **Scalable** - Easy to add new features
- **Modular** - Each part can be tested independently
- **Professional** - Follows industry best practices

Perfect for a university project! 🎓

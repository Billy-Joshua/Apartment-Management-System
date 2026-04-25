<<<<<<< HEAD
# README - Apartment Management System

Quick Start Guide

 What is This?
A complete **Apartment Management System** built with Java (Swing GUI), MySQL, and Socket Programming.

 Features
✅ Role-based user authentication (Admin, Manager, Tenant)  
✅ Complete CRUD operations (Create, Read, Update, Delete)  
✅ MVC Architecture  
✅ MySQL Database with 5 tables  
✅ Java Swing GUI  
✅ Socket Programming for multiple users  
✅ Input validation and error handling  
✅ Professional UI design  

### Technology Stack
- **Language:** Java (JDK 8+)
- **Database:** MySQL 5.7+ (XAMPP)
- **GUI:** Java Swing
- **Architecture:** Model-View-Controller (MVC)
- **Networking:** Socket Programming

4. **Compile & Run:**
   ```bash
   cd "d:\Apartment Management System"
   java -cp src:lib/* com.ams.Main
   ```
5. **Login** with:
   - Admin: `admin` / `admin123`
   - Manager: `manager1` / `manager123`
   - Tenant: `tenant1` / `tenant123`

### Project Structure
```
├── src/com/ams/
│   ├── Main.java                    ← Run this
│   ├── config/DatabaseConfig.java   ← DB connection
│   ├── model/                       ← Data classes
│   ├── controller/                  ← Business logic
│   ├── view/                        ← GUI classes
│   ├── socket/                      ← Server & Client
│   └── utils/                       ← Helpers
├── database_schema.sql              ← Run this in phpMyAdmin
├── DATABASE_SETUP.md                ← Detailed setup guide
├── PROJECT_GUIDE.md                 ← Complete documentation
└── lib/                             ← Put MySQL JAR here
```

### Detailed Documentation
1. **Setup Instructions:** See `DATABASE_SETUP.md`
2. **Project Details:** See `PROJECT_GUIDE.md`
3. **For Presentation:** See PROJECT_GUIDE.md Section 8

### Key Files to Know
| File | Purpose |
|------|---------|
| `Main.java` | Application entry point |
| `LoginFrame.java` | Login UI |
| `AdminDashboard.java` | Admin panel |
| `DatabaseConfig.java` | MySQL connection |
| `AuthController.java` | Authentication logic |
| `database_schema.sql` | Database creation |

### Common Issues & Solutions

**Problem:** "Connection refused"  
**Solution:** Start MySQL in XAMPP Control Panel

**Problem:** "Unknown database"  
**Solution:** Run `database_schema.sql` in phpMyAdmin

**Problem:** MySQL JAR error  
**Solution:** Download & place mysql-connector-java-8.0.jar in `lib/` folder

### Demo Credentials
```
Admin:     admin / admin123
Manager:   manager1 / manager123  
Tenant:    tenant1 / tenant123
```

### Presentation
Use the **PROJECT_GUIDE.md Section 8** - it has a complete 2-3 minute script with slides!


## Next Steps
1. Follow `DATABASE_SETUP.md` for detailed setup
2. Read `PROJECT_GUIDE.md` for complete documentation
3. Review code comments for understanding architecture
4. Test all features with different user roles
5. Prepare presentation using provided script

---

**Questions?** Check the documentation files for detailed explanations.



---

*Version: 1.0.0*  
*Last Updated: April 2024*
=======
# Apartment-Management-System
>>>>>>> f2ef482e05750a4d8445fdd6ce9cbd8f9b744d21

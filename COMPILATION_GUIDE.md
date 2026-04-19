# COMPILATION & EXECUTION GUIDE

## Quick Commands

### **Option 1: One-Line Compilation (Windows)**
```batch
cd /d "d:\Apartment Management System" && javac -d bin -cp src:lib/* src/com/ams/*.java src/com/ams/config/*.java src/com/ams/model/*.java src/com/ams/controller/*.java src/com/ams/view/*.java src/com/ams/utils/*.java src/com/ams/socket/*.java && java -cp bin:lib/* com.ams.Main
```

### **Option 2: Step-by-Step Compilation (Windows)**

**Step 1: Open Command Prompt**
```batch
cd /d "d:\Apartment Management System"
```

**Step 2: Compile All Java Files**
```batch
javac -d bin -cp src:lib/* src/com/ams/*.java
javac -d bin -cp src:lib/* src/com/ams/config/*.java
javac -d bin -cp src:lib/* src/com/ams/model/*.java
javac -d bin -cp src:lib/* src/com/ams/controller/*.java
javac -d bin -cp src:lib/* src/com/ams/view/*.java
javac -d bin -cp src:lib/* src/com/ams/utils/*.java
javac -d bin -cp src:lib/* src/com/ams/socket/*.java
```

**Step 3: Run Application**
```batch
java -cp bin:lib/* com.ams.Main
```

### **Option 3: Quick Run (No Separate Compile)**
```batch
java -cp src:lib/* -encoding UTF-8 com.ams.Main
```

### **Option 4: Linux/Mac**
```bash
cd ~/ApartmentManagementSystem
javac -d bin -cp src:lib/* $(find src -name "*.java")
java -cp bin:lib/* com.ams.Main
```

---

## Compilation Breakdown

```
Source Files (src/)
        │
        ├─ com/ams/*.java              (Main.java)
        ├─ com/ams/config/*.java       (DatabaseConfig.java)
        ├─ com/ams/model/*.java        (User, Tenant, Room, Payment, Contract)
        ├─ com/ams/controller/*.java   (Auth, Tenant, Room, Payment, Contract Controllers)
        ├─ com/ams/view/*.java         (Login, Dashboards, Management UIs)
        ├─ com/ams/socket/*.java       (Server, ClientHandler)
        └─ com/ams/utils/*.java        (Constants, ValidationUtils, DateUtils)
        │
        ▼
    Compile (javac)
        │
        ▼
Binary Files (bin/ or class files in src/)
        │
        ├─ com/ams/*.class
        ├─ com/ams/config/*.class
        ├─ com/ams/model/*.class
        ├─ com/ams/controller/*.class
        ├─ com/ams/view/*.class
        ├─ com/ams/socket/*.class
        └─ com/ams/utils/*.class
        │
        ▼
    Run (java)
        │
        ▼
Application Starts
```

---

## Using VS Code

### **Setup VS Code for Java**

1. **Install Extension Pack for Java**
   - Open VS Code
   - Go to Extensions (Ctrl+Shift+X)
   - Search "Extension Pack for Java"
   - Click Install

2. **Open Project Folder**
   - File → Open Folder
   - Select `d:\Apartment Management System`

3. **Configure Classpath**
   - Create `.classpath` file in root:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <classpath>
       <classpathentry kind="src" path="src"/>
       <classpathentry kind="lib" path="lib/mysql-connector-java-8.0.33.jar"/>
       <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
   </classpath>
   ```

4. **Run Main.java**
   - Open `src/com/ams/Main.java`
   - Right-click → "Run Java"
   - Or press Ctrl+F5

---

## Using NetBeans

1. **Create New Project**
   - File → New Project → Java → Java Application
   - Project Name: ApartmentManagementSystem
   - Location: Select existing source

2. **Add Libraries**
   - Right-click Project → Properties
   - Libraries → Add JAR/Folder
   - Add mysql-connector-java-8.0.jar

3. **Set Main Class**
   - Right-click Project → Properties
   - Sources → Main Class: com.ams.Main

4. **Run Project**
   - F6 or Run → Run Project

---

## Using IntelliJ IDEA

1. **Import Project**
   - File → Open
   - Select project folder
   - Click OK

2. **Configure SDK**
   - File → Project Structure → SDK
   - Select JDK 8+
   - Click OK

3. **Add MySQL JAR**
   - File → Project Structure → Libraries
   - "+" → Java → Select mysql-connector-java-8.0.jar
   - Click OK

4. **Run Application**
   - Right-click Main.java → Run
   - Or press Shift+F10

---

## Troubleshooting Compilation

### **Error: javac: command not found**
**Solution:** Add Java to PATH
- Windows: `C:\Program Files\Java\jdk1.8.0_version\bin`
- Check: `java -version` and `javac -version`

### **Error: The type java.lang.Object cannot be resolved**
**Solution:** Check JDK path and JAVA_HOME
```bash
echo %JAVA_HOME%          # Windows
echo $JAVA_HOME           # Linux/Mac
```

### **Error: cannot access DatabaseConfig**
**Solution:** Compile in correct order
```batch
javac -cp src:lib/* src/com/ams/config/*.java
javac -cp src:lib/* src/com/ams/model/*.java
javac -cp src:lib/* src/com/ams/controller/*.java
```

### **Error: MySQL driver not found**
**Solution:** Ensure mysql-connector-java-8.0.jar is in lib/
```bash
dir lib\          # Windows
ls lib/           # Linux/Mac
```

---

## Running the Server (Socket Programming)

**Terminal 1: Start MySQL**
```bash
# XAMPP Control Panel: Start MySQL
# Or command line:
net start MySQL80    # Windows
mysql -u root        # Linux/Mac
```

**Terminal 2: Start Server**
```bash
cd "d:\Apartment Management System"
java -cp src:lib/* com.ams.socket.Server
```

**Terminal 3: Start Client (Application)**
```bash
cd "d:\Apartment Management System"
java -cp src:lib/* com.ams.Main
```

---

## Creating Executable JAR

**Step 1: Create MANIFEST.MF**
Create file `MANIFEST.MF`:
```
Manifest-Version: 1.0
Main-Class: com.ams.Main
Class-Path: lib/mysql-connector-java-8.0.33.jar
```

**Step 2: Compile**
```bash
javac -d bin -cp src:lib/* $(find src -name "*.java")
```

**Step 3: Create JAR**
```bash
jar cvfm AMS.jar MANIFEST.MF -C bin com -C lib .
```

**Step 4: Run JAR**
```bash
java -jar AMS.jar
```

---

## Common Compilation Flags

```bash
-d <directory>     # Output directory for class files
-cp <paths>        # Classpath (where to find classes)
-encoding <enc>    # Source file encoding
-g                 # Generate debug information
-O                 # Optimize
-verbose           # Print what compiler is doing
-Xlint             # Show warnings
```

---

## Pre-Compilation Checklist

- [ ] MySQL is running (XAMPP Control Panel)
- [ ] Database `apartment_management_system` created
- [ ] Tables created from `database_schema.sql`
- [ ] `lib/mysql-connector-java-8.0.jar` exists
- [ ] All source files are in correct folders
- [ ] Java version is 8 or higher: `java -version`
- [ ] JAVA_HOME is set correctly
- [ ] No syntax errors in code

---

## Pre-Execution Checklist

- [ ] All .class files are present in bin/ or src/
- [ ] MySQL is running
- [ ] Database connection credentials are correct in Constants.java
- [ ] No other application is using port 5555 (for server)
- [ ] Sufficient system memory
- [ ] All external JAR files are on classpath

---

## Expected Console Output

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

Then the Login Window should appear.

---

## Performance Tips

1. **Faster Compilation:**
   ```bash
   javac -g:none -O -cp src:lib/* src/com/ams/*.java
   ```

2. **Lower Memory Usage:**
   ```bash
   java -Xms128m -Xmx256m -cp src:lib/* com.ams.Main
   ```

3. **Incremental Compilation:**
   - Only recompile changed files
   - Use IDE for automatic compilation

---

## Debug Mode

**Compile with Debug Info:**
```bash
javac -g -cp src:lib/* src/com/ams/*.java
```

**Run with Debugger:**
- VS Code: Debug → Start Debugging (F5)
- NetBeans: Debug → Debug Project
- IntelliJ: Debug → Debug Main

---

## Package Structure Summary

```
com.ams                    # Main package
├─ Main.java              # Entry point
├─ config                 # Configuration
├─ model                  # Data models
├─ controller             # Business logic
├─ view                   # GUI components
├─ socket                 # Network communication
└─ utils                  # Utilities
```

Each package is isolated and can be compiled/tested independently.

---

**You're ready to compile and run!**

If you face any issues, check:
1. Console error messages
2. DATABASE_SETUP.md for configuration
3. Ensure all prerequisites are installed
4. Check JAVA_HOME and classpath

Good luck! 🚀

# Debug Steps - MySQL Connection

## Βήμα 1: Έλεγξε αν η εφαρμογή ξεκινάει

Κοίταξε στο console/terminal όταν τρέχεις την εφαρμογή:

**Αν δεις:**
```
Started FindBombsApplication in X seconds
```
→ ✅ Η εφαρμογή ξεκίνησε

**Αν δεις error πριν το "Started":**
→ ❌ Υπάρχει πρόβλημα στη σύνδεση

## Βήμα 2: Κοίταξε τα Logs κατά το Startup

Ψάξε για αυτά τα messages:

### ✅ Good Signs:
```
Hibernate: create table stored_files ...
Hibernate: create table graffiti_pieces ...
```

### ❌ Error Signs:

**"Communications link failure"**
→ Το MySQL server δεν τρέχει
**Solution:** Ξεκίνα το MySQL server

**"Access denied for user 'root'@'localhost'"**
→ Το password είναι λάθος
**Solution:** Ελέγξτε το password στο Workbench

**"Unknown database 'findbombsdb'"**
→ Η βάση δεν υπάρχει
**Solution:** 
```sql
CREATE DATABASE findbombsdb;
```

**"Table 'findbombsdb.stored_files' doesn't exist"**
→ Το table δεν έχει δημιουργηθεί
**Solution:** Restart την εφαρμογή (το Spring Boot θα το δημιουργήσει)

## Βήμα 3: Test στο MySQL Workbench

1. Άνοιξε MySQL Workbench
2. Συνδέσου με:
   - Username: `root`
   - Password: `12345` (ή όποιο έχεις)
3. Αν συνδεθείς → ✅ Το password είναι σωστό
4. Αν δεν συνδεθείς → ❌ Το password είναι λάθος

## Βήμα 4: Δημιούργησε τη βάση (αν δεν υπάρχει)

Στο Workbench, εκτέλεσε:
```sql
CREATE DATABASE IF NOT EXISTS findbombsdb;
USE findbombsdb;
```

## Βήμα 5: Test Connection

Μετά το restart, άνοιξε:
```
http://localhost:8080/api/files/test
```

**Αν δεις:** "✅ Database connection OK!" → ✅ Δουλεύει!
**Αν δεις error:** Copy-paste το error message

## Common Issues

### Issue 1: MySQL Server δεν τρέχει
**Check:** Services → MySQL → Start
**Or:** Command line: `net start MySQL` (Windows)

### Issue 2: Wrong Port
**Check:** Default MySQL port είναι 3306
**If different:** Άλλαξε στο properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:YOUR_PORT/findbombsdb...
```

### Issue 3: Firewall Blocking
**Solution:** Allow MySQL through firewall

### Issue 4: Connection Timeout
**Solution:** Προσθήκη timeout στο URL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/findbombsdb?connectTimeout=5000&socketTimeout=5000
```


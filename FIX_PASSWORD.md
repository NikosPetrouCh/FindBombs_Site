# Fix MySQL Password Issue

## Το Error:
```
Access denied for user 'root'@'172.18.0.1' (using password: YES)
```

## Λύσεις:

### Λύση 1: Βρες το σωστό password

1. Άνοιξε MySQL Workbench
2. Δοκίμασε να συνδεθείς με διαφορετικά passwords
3. Όταν βρεις το σωστό, άλλαξέ το στο `application-dev.properties` (γραμμή 5)

### Λύση 2: Άλλαξε το password στο MySQL

Στο Workbench (αν μπορείς να συνδεθείς), εκτέλεσε:

```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY '12345';
ALTER USER 'root'@'%' IDENTIFIED BY '12345';
FLUSH PRIVILEGES;
```

### Λύση 3: Δημιούργησε νέο user (Προτεινόμενη)

Στο Workbench, εκτέλεσε:

```sql
CREATE DATABASE IF NOT EXISTS findbombsdb;

CREATE USER 'findbombs'@'%' IDENTIFIED BY 'findbombs123';
GRANT ALL PRIVILEGES ON findbombsdb.* TO 'findbombs'@'%';
FLUSH PRIVILEGES;
```

Μετά άλλαξε το `application-dev.properties`:
- Username: `findbombs`
- Password: `findbombs123`

### Λύση 4: Χρήση H2 (Γρήγορη)

Αν θέλεις να δοκιμάσεις γρήγορα, χρησιμοποίησε H2. Δες το `QUICK_FIX_H2.md`


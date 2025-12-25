# Alternative: Setup New MySQL User

Αν το root password δεν δουλεύει, δημιούργησε νέο user:

## Στο MySQL Workbench:

```sql
-- Δημιούργησε τη βάση
CREATE DATABASE IF NOT EXISTS findbombsdb;

-- Δημιούργησε νέο user
CREATE USER 'findbombs'@'%' IDENTIFIED BY 'findbombs123';

-- Δώσε privileges
GRANT ALL PRIVILEGES ON findbombsdb.* TO 'findbombs'@'%';

-- Εφαρμογή αλλαγών
FLUSH PRIVILEGES;
```

## Άλλαξε το application-dev.properties:

```properties
spring.datasource.username=findbombs
spring.datasource.password=findbombs123
```

## Restart την εφαρμογή


# Οδηγός: Πώς να δεις Uploads στο MySQL Workbench

## Βήμα 1: Προετοιμασία MySQL

1. **Άνοιξε MySQL Workbench**
2. **Συνδέσου** στο localhost (ή όπου έχεις MySQL)
   - Host: `localhost`
   - Port: `3306`
   - Username: `root` (ή το δικό σου)
   - Password: (το δικό σου password)

## Βήμα 2: Δημιούργησε τη βάση (αν δεν υπάρχει)

Στο MySQL Workbench, εκτέλεσε αυτό το query:

```sql
CREATE DATABASE IF NOT EXISTS findbombsdb;
USE findbombsdb;
```

## Βήμα 3: Ρύθμισε το Password (αν χρειάζεται)

Αν το MySQL σου έχει password, άνοιξε το αρχείο:
```
src/main/resources/application-dev.properties
```

Και άλλαξε τη γραμμή 5:
```properties
spring.datasource.password=${MYSQL_PASSWORD:το_password_σου}
```

Ή απλά:
```properties
spring.datasource.password=το_password_σου
```

## Βήμα 4: Τρέξε την εφαρμογή

1. Άνοιξε terminal στο project folder
2. Τρέξε: `./gradlew bootRun` (ή από IDE)
3. Περίμενε να ξεκινήσει (θα δεις "Started FindBombsApplication")

## Βήμα 5: Δες το Table στο Workbench

1. **Refresh** το schema στο Workbench (κάνε δεξί κλικ στο `findbombsdb` → Refresh All)
2. **Επέκτεινε** το `findbombsdb` → `Tables`
3. **Θα δεις** το table `stored_files` (αν έχει δημιουργηθεί)

## Βήμα 6: Κάνε Upload και Δες την Αλλαγή

### 6.1 Κάνε Upload
1. Άνοιξε: `http://localhost:8080/upload`
2. Επέλεξε εικόνα
3. Συμπλήρωσε τα στοιχεία
4. Κάνε Upload

### 6.2 Δες το Upload στο Workbench

**Μέθοδος 1: Refresh και Table Data**
1. Στο Workbench, δεξί κλικ στο `stored_files` table
2. Επίλεξε **"Select Rows - Limit 1000"**
3. Θα δεις τα uploaded files!

**Μέθοδος 2: Query**
Εκτέλεσε αυτό το query:

```sql
USE findbombsdb;

SELECT 
    file_id,
    original_filename,
    content_type,
    file_size,
    uploaded_at,
    is_active,
    LENGTH(file_data) as data_size_bytes
FROM stored_files
WHERE is_active = 1
ORDER BY uploaded_at DESC;
```

## Βήμα 7: Δες το Binary Data (Optional)

Για να δεις το file_data (θα είναι hex string):

```sql
SELECT 
    file_id,
    original_filename,
    SUBSTRING(HEX(file_data), 1, 100) as file_data_preview
FROM stored_files
WHERE file_id = 1;  -- Replace with your file_id
```

## Troubleshooting

### Problem: "Table doesn't exist"
**Solution:** 
- Βεβαιώσου ότι η εφαρμογή τρέχει
- Το Spring Boot δημιουργεί το table αυτόματα με `ddl-auto=update`
- Refresh το schema στο Workbench

### Problem: "Access denied"
**Solution:**
- Ελέγξτε username/password στο `application-dev.properties`
- Βεβαιώσου ότι το MySQL server τρέχει

### Problem: "Connection refused"
**Solution:**
- Ελέγξτε ότι το MySQL server είναι running
- Ελέγξτε το port (default: 3306)

## Real-time Monitoring

Για να δεις τα uploads σε real-time:

1. Άνοιξε ένα query tab στο Workbench
2. Εκτέλεσε:
```sql
SELECT COUNT(*) as total_files FROM stored_files WHERE is_active = 1;
```
3. Κάνε upload
4. Εκτέλεσε ξανά το query (F5) - θα δεις το count να αυξάνεται!

## Προβολή Εικόνας

Μετά το upload, μπορείς να δεις την εικόνα στο browser:
```
http://localhost:8080/api/files/{file_id}
```

Παράδειγμα: Αν το `file_id = 1`, άνοιξε:
```
http://localhost:8080/api/files/1
```


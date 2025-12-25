# Database File Storage - MySQL Workbench Integration

## Overview
Τα uploaded files (εικόνες) αποθηκεύονται τώρα στη βάση δεδομένων MySQL ως BLOB (Binary Large Object) αντί για filesystem. Αυτό σημαίνει ότι μπορείτε να τα δείτε και να τα διαχειριστείτε μέσω του MySQL Workbench.

## Database Schema

### Table: `stored_files`
Αυτή η table αποθηκεύει όλα τα uploaded files:

| Column | Type | Description |
|--------|------|-------------|
| `file_id` | BIGINT | Primary key, auto-increment |
| `original_filename` | VARCHAR(255) | Το αρχικό όνομα του αρχείου |
| `content_type` | VARCHAR(100) | MIME type (π.χ. "image/jpeg", "image/png") |
| `file_size` | BIGINT | Μέγεθος αρχείου σε bytes |
| `file_data` | LONGBLOB | Τα binary data του αρχείου |
| `uploaded_at` | DATETIME | Ημερομηνία/ώρα upload |
| `is_active` | BOOLEAN | Αν το αρχείο είναι ενεργό (soft delete) |
| `created_at` | DATETIME | Created timestamp (από AbstractEntity) |
| `updated_at` | DATETIME | Updated timestamp (από AbstractEntity) |

### Table: `graffiti_pieces`
Η `image_url` column τώρα περιέχει paths όπως `/api/files/{file_id}` που δείχνουν στο file ID στη βάση.

## Viewing Files in MySQL Workbench

1. **Ανοίξτε το MySQL Workbench** και συνδεθείτε στη βάση δεδομένων
2. **Επιλέξτε τη βάση** `findbombsdb` (ή όποια βάση χρησιμοποιείτε)
3. **Εκτελέστε query:**
   ```sql
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

4. **Για να δείτε το binary data:**
   ```sql
   SELECT file_id, original_filename, file_data
   FROM stored_files
   WHERE file_id = 1;  -- Replace with actual file_id
   ```
   **Σημείωση:** Το `file_data` θα εμφανιστεί ως hex string στο Workbench.

## Accessing Files via API

Τα files είναι προσβάσιμα μέσω REST API:

```
GET /api/files/{fileId}
```

**Παράδειγμα:**
- `http://localhost:8080/api/files/1` - Επιστρέφει το file με ID 1

## Configuration

### MySQL Connection
Στο `application-dev.properties`, uncomment τις MySQL lines:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/findbombsdb?useSSL=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### File Size Limits
- Default max file size: 10MB (10485760 bytes)
- Configure στο `application.properties`: `app.upload.max-size=10485760`

## Benefits

✅ **Centralized Storage** - Όλα τα files σε ένα μέρος  
✅ **Database Backup** - Τα files περιλαμβάνονται στα backups  
✅ **Transaction Support** - Atomic operations  
✅ **Easy Management** - Διαχείριση μέσω SQL queries  
✅ **Workbench Integration** - Προβολή και διαχείριση από MySQL Workbench  

## Migration Notes

- Παλιά files που ήταν στο filesystem θα συνεχίσουν να λειτουργούν (backward compatibility)
- Νέα uploads αποθηκεύονται στη βάση
- Για migration παλιών files, μπορείτε να δημιουργήσετε migration script

## Troubleshooting

**Problem:** Files δεν φαίνονται  
**Solution:** Ελέγξτε ότι το `is_active = 1` στη βάση

**Problem:** Large files fail  
**Solution:** Αυξήστε το `max_allowed_packet` στο MySQL:
```sql
SET GLOBAL max_allowed_packet=16777216;  -- 16MB
```


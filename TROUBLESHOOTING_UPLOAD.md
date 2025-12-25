# Troubleshooting: Upload δεν αποθηκεύεται στη βάση

## Βήμα 1: Έλεγξε αν η εφαρμογή συνδέεται στη MySQL

1. **Τρέξε την εφαρμογή** (./gradlew bootRun)
2. **Άνοιξε browser**: `http://localhost:8080/api/files/test`
3. **Αν δεις**: "Database connection OK! Total files in database: X" → ✅ Συνδέεται
4. **Αν δεις error**: ❌ Δεν συνδέεται

## Βήμα 2: Έλεγξε τα Logs

Κοίταξε στο console/terminal όταν τρέχει η εφαρμογή:

**Αν δεις:**
```
Hibernate: create table stored_files ...
```
→ ✅ Το table δημιουργείται

**Αν δεις:**
```
Access denied for user 'root'@'localhost'
```
→ ❌ Password λάθος

**Αν δεις:**
```
Communications link failure
```
→ ❌ MySQL server δεν τρέχει

## Βήμα 3: Έλεγξε στο MySQL Workbench

Εκτέλεσε:
```sql
USE findbombsdb;
SHOW TABLES;
```

**Αν δεις `stored_files`**: ✅ Το table υπάρχει
**Αν δεν δεις τίποτα**: ❌ Το table δεν έχει δημιουργηθεί

## Βήμα 4: Έλεγξε το Password

Αν το MySQL σου έχει password, άνοιξε:
```
src/main/resources/application-dev.properties
```

Και άλλαξε τη γραμμή 5:
```properties
spring.datasource.password=το_πραγματικό_σου_password
```

## Βήμα 5: Έλεγξε αν χρησιμοποιεί H2

Αν η εφαρμογή χρησιμοποιεί H2 αντί για MySQL:

1. Άνοιξε: `http://localhost:8080/h2-console`
2. Αν ανοίγει → Χρησιμοποιεί H2, όχι MySQL!

**Λύση**: Βεβαιώσου ότι στο `application-dev.properties`:
- Οι MySQL lines είναι uncommented (γραμμές 3-6)
- Οι H2 lines είναι commented (γραμμές 9-14)

## Βήμα 6: Restart την εφαρμογή

Μετά από αλλαγές στο properties:
1. **Stop** την εφαρμογή
2. **Start** ξανά
3. Περίμενε να δεις: "Started FindBombsApplication"

## Βήμα 7: Test Upload με Logs

Κάνε upload και κοίταξε τα logs:

**Αν δεις:**
```
File saved to database with id=1, filename=photo.jpg, size=12345 bytes
```
→ ✅ Το file αποθηκεύτηκε!

**Αν δεις error**: Copy-paste το error message

## Common Errors

### Error: "Table 'findbombsdb.stored_files' doesn't exist"
**Solution**: 
- Restart την εφαρμογή
- Το Spring Boot θα δημιουργήσει το table με `ddl-auto=update`

### Error: "Access denied"
**Solution**: 
- Ελέγξτε username/password στο `application-dev.properties`

### Error: "Communications link failure"
**Solution**: 
- Βεβαιώσου ότι το MySQL server τρέχει
- Ελέγξτε το port (default: 3306)


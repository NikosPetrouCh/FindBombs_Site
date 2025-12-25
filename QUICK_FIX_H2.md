# Quick Fix: Χρήση H2 για Testing

Αν έχεις πρόβλημα με MySQL, μπορείς να χρησιμοποιήσεις H2 προσωρινά για testing:

## Βήμα 1: Άλλαξε το application-dev.properties

Σχολίασε τις MySQL lines και uncomment τις H2 lines:

```properties
## DB CONNECTION - Using H2 for quick start ##
# For MySQL, uncomment these and comment H2 lines:
# spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB:findbombsdb}?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
# spring.datasource.username=${MYSQL_USER:root}
# spring.datasource.password=${MYSQL_PASSWORD:}
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# H2 Database (in-memory for development)
spring.datasource.url=jdbc:h2:file:~/findbombsdb;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Βήμα 2: Restart την εφαρμογή

## Βήμα 3: Test

1. `http://localhost:8080/api/files/test` → Θα δουλεύει!
2. `http://localhost:8080/h2-console` → Για να δεις τη βάση
3. JDBC URL: `jdbc:h2:file:~/findbombsdb`

## Σημείωση

H2 είναι για development/testing. Για production χρησιμοποίησε MySQL.


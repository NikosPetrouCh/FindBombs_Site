# 🎨 FindBombs - Graffiti Showcase Platform

Μια web εφαρμογή για την καταγραφή, προβολή και διαχείριση graffiti στην Ελλάδα. Οι χρήστες μπορούν να ανεβάζουν, να αναζητούν και να περιηγούνται σε graffiti από διάφορες ελληνικές πόλεις.

## 📋 Περιεχόμενα

- [Τεχνολογίες](#τεχνολογίες)
- [Features](#features)
- [Προαπαιτούμενα](#προαπαιτούμενα)
- [Εγκατάσταση](#εγκατάσταση)
- [Configuration](#configuration)
- [Εκτέλεση](#εκτέλεση)
- [Database Setup](#database-setup)
- [File Upload](#file-upload)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)

## 🛠️ Τεχνολογίες

- **Backend**: Java 21, Spring Boot 3.5.3
- **Database**: MySQL 8.0 (production), H2 (development)
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Maps**: Leaflet.js
- **Build Tool**: Gradle
- **Fonts**: Google Fonts (Poppins, Bungee, Creepster, Frijole)

## ✨ Features

- 📤 **File Upload**: Upload εικόνων graffiti με αποθήκευση στη βάση δεδομένων (BLOB)
- 🗺️ **Interactive Map**: Χάρτης Ελλάδας με markers για κάθε πόλη
- 🔍 **Search**: Full-text search σε graffiti pieces
- 📸 **Gallery**: Paginated gallery με filters
- 🏛️ **City Views**: Προβολή graffiti ανά πόλη
- ⭐ **Rating System**: Rating και view count για κάθε piece
- 🏷️ **Tags & Categories**: Κατηγοριοποίηση με tags, styles, artists
- 🎨 **Dark Graffiti Theme**: Modern dark UI με graffiti-style typography

## 📦 Προαπαιτούμενα

- Java 21 (Amazon Corretto recommended)
- MySQL 8.0+ (ή H2 για development)
- MySQL Workbench (optional, για database management)
- Gradle 8.14.3+

## 🚀 Εγκατάσταση

### 1. Clone το repository

```bash
git clone <repository-url>
cd FindBombs
```

### 2. Database Setup

#### Option A: MySQL (Production)

1. Άνοιξε MySQL Workbench (port 3307)
2. Δημιούργησε τη βάση:
```sql
CREATE DATABASE IF NOT EXISTS findbombsdb;
```

3. Δώσε πρόσβαση στον user:
```sql
GRANT ALL PRIVILEGES ON findbombsdb.* TO 'user8'@'%';
FLUSH PRIVILEGES;
```

4. Δημιούργησε το `stored_files` table:
```sql
USE findbombsdb;

CREATE TABLE IF NOT EXISTS `stored_files` (
    `file_id` BIGINT NOT NULL AUTO_INCREMENT,
    `original_filename` VARCHAR(255) NOT NULL,
    `content_type` VARCHAR(100) NOT NULL,
    `file_size` BIGINT NOT NULL,
    `file_data` MEDIUMBLOB NOT NULL,
    `uploaded_at` DATETIME NOT NULL,
    `is_active` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME,
    PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### Option B: H2 (Development - Quick Start)

Επεξεργάσου το `src/main/resources/application-dev.properties`:
- Comment τις MySQL lines
- Uncomment τις H2 lines

### 3. Configuration

Επεξεργάσου το `src/main/resources/application-dev.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/findbombsdb?useSSL=true&serverTimezone=UTC
spring.datasource.username=user8
spring.datasource.password=12345
```

**Σημείωση**: Άλλαξε το username/password ανάλογα με τη δική σου setup.

## ▶️ Εκτέλεση

### Development Mode

```bash
./gradlew bootRun
```

Η εφαρμογή θα τρέξει στο: `http://localhost:8080`

### Build JAR

```bash
./gradlew build
```

Το JAR file θα βρίσκεται στο: `build/libs/findbombs.jar`

### Run JAR

```bash
java -jar build/libs/findbombs.jar
```

## 🗄️ Database Setup

### Tables

Το Spring Boot δημιουργεί αυτόματα τα tables με `ddl-auto=update`:

- `cities` - Πόλεις
- `graffiti_pieces` - Graffiti pieces
- `stored_files` - Uploaded files (BLOB storage)
- `artists` - Καλλιτέχνες
- `graffiti_styles` - Στυλ graffiti
- `locations` - Τοποθεσίες
- `tags` - Tags
- `piece_tags` - Many-to-many relationship

### Viewing Files in MySQL Workbench

```sql
USE findbombsdb;

-- Δες όλα τα uploaded files
SELECT 
    file_id,
    original_filename,
    content_type,
    file_size,
    uploaded_at,
    is_active
FROM stored_files
WHERE is_active = 1
ORDER BY uploaded_at DESC;

-- Count files
SELECT COUNT(*) as total_files FROM stored_files WHERE is_active = 1;
```

## 📤 File Upload

### Upload Process

1. Ο χρήστης ανεβάζει εικόνα μέσω `/upload`
2. Το file αποθηκεύεται στη βάση ως BLOB στο `stored_files` table
3. Το file ID αποθηκεύεται στο `graffiti_pieces.image_url` ως `/api/files/{fileId}`
4. Το file είναι προσβάσιμο μέσω: `http://localhost:8080/api/files/{fileId}`

### File Storage

- **Location**: Database (BLOB column)
- **Max Size**: 10MB (configurable)
- **Allowed Types**: Images only (image/*)
- **Table**: `stored_files`

### Accessing Uploaded Files

```
GET /api/files/{fileId}
```

Παράδειγμα: `http://localhost:8080/api/files/1`

## 📁 Project Structure

```
FindBombs/
├── src/
│   ├── main/
│   │   ├── java/gr/aueb/cf/findbombs/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST & MVC Controllers
│   │   │   ├── core/
│   │   │   │   └── exceptions/  # Custom exceptions
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── mapper/          # Entity-DTO mappers
│   │   │   ├── model/           # JPA Entities
│   │   │   ├── repository/     # JPA Repositories
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── static/          # CSS, JS, Images
│   │       └── templates/       # Thymeleaf templates
│   └── test/                    # Tests
├── build.gradle                 # Gradle build file
└── README.md                    # This file
```

## 🔌 API Endpoints

### Public Endpoints

- `GET /` - Αρχική σελίδα
- `GET /gallery` - Gallery με pagination
- `GET /city/{cityId}` - Graffiti ανά πόλη
- `GET /search?q={query}` - Αναζήτηση
- `GET /upload` - Upload form
- `POST /upload` - Upload file
- `GET /api/files/{fileId}` - Get uploaded file
- `GET /api/files/test` - Test database connection
- `GET /about` - About page
- `GET /contact` - Contact page

## 🎨 UI Features

- **Dark Theme**: Μαύρο background με graffiti-style colors
- **Sharp Corners**: Angular design (4px, 8px, 12px border-radius)
- **Graffiti Fonts**: Bungee, Creepster, Frijole για headings
- **Icon-only Buttons**: Home, Gallery, Upload, Language buttons
- **Interactive Map**: Leaflet.js με city markers
- **Responsive Design**: Mobile-friendly

## 🔧 Configuration Files

- `application.properties` - Main configuration
- `application-dev.properties` - Development profile (MySQL/H2)
- `build.gradle` - Dependencies & build config

## 📝 Development Notes

### Database Connection

- **Default Port**: 3307 (configurable)
- **Username**: user8 (configurable)
- **Password**: 12345 (configurable)
- **Database**: findbombsdb

### File Upload Limits

- **Max File Size**: 10MB (10485760 bytes)
- **Allowed Types**: image/* only
- **Storage**: Database (MEDIUMBLOB)

### Logging

- SQL queries: Enabled (`show_sql=true`)
- Hibernate logs: Enabled for debugging

## 🐛 Troubleshooting

### Problem: "Table doesn't exist"
**Solution**: 
- Restart την εφαρμογή (Spring Boot δημιουργεί tables με `ddl-auto=update`)
- Ή δημιούργησε το table manually (δες `create_stored_files_table.sql`)

### Problem: "Access denied"
**Solution**: 
- Ελέγξτε username/password στο `application-dev.properties`
- Βεβαιώσου ότι ο user έχει privileges στη βάση

### Problem: "Connection refused"
**Solution**: 
- Ελέγξτε ότι το MySQL server τρέχει
- Ελέγξτε το port (default: 3307)

### Problem: Files δεν φαίνονται
**Solution**: 
- Ελέγξτε ότι το `stored_files` table υπάρχει
- Ελέγξτε ότι `is_active = 1` στη βάση

## 📚 Documentation

- `DATABASE_FILES_README.md` - Database file storage guide
- `WORKBENCH_GUIDE.md` - MySQL Workbench integration
- `TROUBLESHOOTING_UPLOAD.md` - Upload troubleshooting
- `create_stored_files_table.sql` - SQL script για table creation

## 👤 Author

FindBombs Project

## 📄 License

This project is for educational purposes.

---

**Happy Graffiti Hunting! 🎨🪣**


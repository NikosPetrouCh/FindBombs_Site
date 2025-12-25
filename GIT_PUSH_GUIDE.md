# Git Push Guide

## Προετοιμασία για Push

### 1. Βεβαιώσου ότι δεν έχεις sensitive data

Το `application-dev.properties` χρησιμοποιεί environment variables:
- `${MYSQL_USER:user8}` - Default: user8
- `${MYSQL_PASSWORD:}` - Empty default (set via environment)

### 2. Set Environment Variables (Optional)

Αν θέλεις να χρησιμοποιήσεις environment variables:

**Windows (PowerShell):**
```powershell
$env:MYSQL_USER="user8"
$env:MYSQL_PASSWORD="12345"
```

**Windows (CMD):**
```cmd
set MYSQL_USER=user8
set MYSQL_PASSWORD=12345
```

**Linux/Mac:**
```bash
export MYSQL_USER=user8
export MYSQL_PASSWORD=12345
```

### 3. Git Commands

```bash
# Check status
git status

# Add all files
git add .

# Commit
git commit -m "Initial commit: FindBombs graffiti showcase platform with database file storage"

# Push (αν έχεις remote)
git push origin main
```

### 4. Αν δεν έχεις remote repository

```bash
# Create new repo on GitHub, then:
git remote add origin <your-repo-url>
git branch -M main
git push -u origin main
```

## Files που ΔΕΝ θα push-αρθούν (από .gitignore)

- `build/` - Build artifacts
- `*.log` - Log files
- `src/main/resources/static/images/uploads/*` - Uploaded files (stored in DB)
- `.gradle/` - Gradle cache
- IDE files (`.idea/`, `.vscode/`)

## Security Note

Το password στο `application-dev.properties` είναι empty by default. 
Οι χρήστες πρέπει να set-άρουν το `MYSQL_PASSWORD` environment variable.


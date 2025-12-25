package gr.aueb.cf.findbombs.controller;

import gr.aueb.cf.findbombs.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.findbombs.model.StoredFile;
import gr.aueb.cf.findbombs.repository.StoredFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final StoredFileRepository storedFileRepository;

    @GetMapping("/api/files/test")
    public ResponseEntity<String> testDatabase() {
        try {
            long count = storedFileRepository.count();
            return ResponseEntity.ok("✅ Database connection OK! Total files in database: " + count);
        } catch (Exception e) {
            log.error("Database test failed", e);
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("❌ Database Error:\n");
            errorMsg.append("Type: ").append(e.getClass().getSimpleName()).append("\n");
            errorMsg.append("Message: ").append(e.getMessage()).append("\n");
            
            if (e.getCause() != null) {
                errorMsg.append("Cause: ").append(e.getCause().getClass().getSimpleName()).append(" - ").append(e.getCause().getMessage()).append("\n");
            }
            
            // Check for specific MySQL errors
            String errorMessage = e.getMessage();
            if (errorMessage != null) {
                if (errorMessage.contains("Communications link failure") || errorMessage.contains("Connection refused")) {
                    errorMsg.append("\n💡 Solution: Check if MySQL server is running on port 3307");
                } else if (errorMessage.contains("Access denied")) {
                    errorMsg.append("\n💡 Solution: Check username/password in application-dev.properties");
                } else if (errorMessage.contains("Unknown database")) {
                    errorMsg.append("\n💡 Solution: Create database: CREATE DATABASE findbombsdb;");
                } else if (errorMessage.contains("Table") && errorMessage.contains("doesn't exist")) {
                    errorMsg.append("\n💡 Solution: Restart the application - Spring Boot will create the table");
                }
            }
            
            return ResponseEntity.status(500).body(errorMsg.toString());
        }
    }

    @GetMapping("/api/files/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
        try {
            StoredFile file = storedFileRepository.findByIdAndIsActiveTrue(fileId)
                    .orElseThrow(() -> new EntityNotFoundException("File", "File not found"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(file.getContentType()));
            headers.setContentLength(file.getFileSize());
            headers.setContentDispositionFormData("inline", file.getOriginalFilename());
            headers.setCacheControl("max-age=3600");

            return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error("File with id={} not found", fileId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving file with id={}", fileId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


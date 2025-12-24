package gr.aueb.cf.findbombs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.upload.max-size:10485760}") // 10MB default
    private long maxFileSize;

    /**
     * Saves uploaded file to the filesystem and returns the relative path
     */
    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }

        // Validate file size
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size of " + (maxFileSize / 1024 / 1024) + "MB");
        }

        // Validate file type (only images)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return relative path for database storage (relative to static root)
        String relativePath = "images/uploads/" + uniqueFilename;
        log.info("File saved successfully: {}", relativePath);
        
        return relativePath;
    }

    /**
     * Deletes a file from the filesystem
     */
    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(uploadDir).resolve(Paths.get(filePath).getFileName());
            if (Files.exists(path)) {
                Files.delete(path);
                log.info("File deleted: {}", path);
            }
        } catch (IOException e) {
            log.error("Error deleting file: {}", filePath, e);
        }
    }

    /**
     * Gets the absolute path for serving files
     */
    public Path getUploadPath() {
        return Paths.get(uploadDir);
    }
}


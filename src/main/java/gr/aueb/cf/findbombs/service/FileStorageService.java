package gr.aueb.cf.findbombs.service;

import gr.aueb.cf.findbombs.model.StoredFile;
import gr.aueb.cf.findbombs.repository.StoredFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    private final StoredFileRepository storedFileRepository;

    @Value("${app.upload.max-size:10485760}") // 10MB default
    private long maxFileSize;

    /**
     * Saves uploaded file to the database and returns the file ID
     */
    @Transactional
    public Long saveFile(MultipartFile file) throws IOException {
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

        // Read file data
        byte[] fileData = file.getBytes();

        // Create StoredFile entity
        StoredFile storedFile = new StoredFile();
        storedFile.setOriginalFilename(file.getOriginalFilename());
        storedFile.setContentType(contentType);
        storedFile.setFileSize(file.getSize());
        storedFile.setFileData(fileData);
        storedFile.setIsActive(true);

        // Save to database
        StoredFile savedFile = storedFileRepository.save(storedFile);
        log.info("File saved to database with id={}, filename={}, size={} bytes", 
                savedFile.getId(), savedFile.getOriginalFilename(), savedFile.getFileSize());
        
        return savedFile.getId();
    }

    /**
     * Gets file ID from URL path (for backward compatibility)
     * If path starts with /api/files/, extract the ID
     */
    public Long getFileIdFromPath(String path) {
        if (path != null && path.startsWith("/api/files/")) {
            try {
                String idStr = path.substring("/api/files/".length());
                return Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                log.warn("Invalid file ID in path: {}", path);
            }
        }
        return null;
    }

    /**
     * Deletes a file from the database (soft delete)
     */
    @Transactional
    public void deleteFile(Long fileId) {
        try {
            StoredFile file = storedFileRepository.findById(fileId)
                    .orElse(null);
            if (file != null) {
                file.setIsActive(false);
                storedFileRepository.save(file);
                log.info("File soft-deleted: id={}", fileId);
            }
        } catch (Exception e) {
            log.error("Error deleting file: id={}", fileId, e);
        }
    }

    /**
     * Gets file by ID
     */
    public StoredFile getFile(Long fileId) {
        return storedFileRepository.findByIdAndIsActiveTrue(fileId).orElse(null);
    }
}


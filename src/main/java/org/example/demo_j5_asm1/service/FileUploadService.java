package org.example.demo_j5_asm1.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadService {
    
    private static final String UPLOAD_DIR = "uploads/images/";
    
    static {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            log.error("Could not create upload directory", e);
        }
    }
    
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        
        // Save file
        Path targetPath = Paths.get(UPLOAD_DIR + filename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        log.info("File uploaded successfully: {}", filename);
        return "/uploads/images/" + filename;
    }
    
    public void deleteFile(String filePath) {
        if (filePath != null && filePath.startsWith("/uploads/images/")) {
            try {
                Path path = Paths.get("." + filePath);
                Files.deleteIfExists(path);
                log.info("File deleted: {}", filePath);
            } catch (IOException e) {
                log.error("Could not delete file: {}", filePath, e);
            }
        }
    }
}

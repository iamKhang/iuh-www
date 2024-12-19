package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {
    private final String baseUploadDir = "uploads/";
    private final String logoUploadDir = baseUploadDir + "logos/";
    private final String avatarUploadDir = baseUploadDir + "avatars/";
    
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(baseUploadDir));
            Files.createDirectories(Path.of(logoUploadDir));
            Files.createDirectories(Path.of(avatarUploadDir));
            
            Path defaultImagePath = Path.of(baseUploadDir + "default_img.png");
            if (!Files.exists(defaultImagePath)) {
                try (InputStream is = getClass().getResourceAsStream("/static/images/default_img.png")) {
                    if (is != null) {
                        Files.copy(is, defaultImagePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directories!", e);
        }
    }

    public String saveLogoFile(MultipartFile file) {
        return saveFile(file, logoUploadDir);
    }

    public String saveAvatarFile(MultipartFile file) {
        return saveFile(file, avatarUploadDir);
    }

    private String saveFile(MultipartFile file, String uploadDir) {
        try {
            String fileName = System.currentTimeMillis() + "_" + 
                            StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = Path.of(uploadDir + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }
} 
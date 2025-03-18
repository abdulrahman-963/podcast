package com.podcast.podcast.service;


import com.podcast.podcast.config.Constants;
import com.podcast.podcast.exception.AttachmentException;
import com.podcast.podcast.model.dto.AttachmentDTO;
import com.podcast.podcast.repository.AttachmentProperties;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentProperties properties;

    public AttachmentDTO storeFile(MultipartFile file, String serviceName) {
        validateFile(file);

        try {
            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileExtension = FilenameUtils.getExtension(originalFilename);
            String storedFilename = UUID.randomUUID() + "." + fileExtension;

            // Create date-based path structure
            LocalDate today = LocalDate.now();
            String datePath = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Create full path: basePath/serviceName/datePath
            Path targetLocation = getTargetLocation(serviceName, datePath);
            Files.createDirectories(targetLocation);

            // Path where the file will be stored
            Path filePath = targetLocation.resolve(storedFilename);

            // Copy file to target location (replacing existing file with the same name)
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Create and return attachment metadata
            AttachmentDTO attachment = AttachmentDTO.builder()
                    .filename(storedFilename)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(getRelativePath(serviceName, datePath, storedFilename))
                    .downloadUrl(buildDownloadUrl(getRelativePath(serviceName, datePath, storedFilename)))
                    .build();

            log.info("File stored successfully: {}", attachment.getFilePath());
            return attachment;

        } catch (IOException ex) {
            throw new AttachmentException("Failed to store file", ex);
        }
    }

    public Resource loadFileAsResource(String filePath) {
        Resource resource = null;
        try {
            Path file = Paths.get(properties.getBasePath()).resolve(filePath).normalize();
            resource = new UrlResource(file.toUri());

        } catch (Exception ex) {
            throw new AttachmentException("File could not be read: " + filePath, ex);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new EntityNotFoundException("File not found: " + filePath);
        }
    }

    public boolean deleteFile(String filePath) {
        try {
            Path file = Paths.get(properties.getBasePath()).resolve(filePath).normalize();
            return Files.deleteIfExists(file);
        } catch (IOException ex) {
            throw new AttachmentException("Could not delete file: " + filePath, ex);
        }
    }

    public List<AttachmentDTO> listFiles(String serviceName) {
        Path servicePath = Paths.get(properties.getBasePath()).resolve(serviceName);
        List<AttachmentDTO> attachments = new ArrayList<>();

        if (!Files.exists(servicePath)) {
            return attachments;
        }

        try (Stream<Path> paths = Files.walk(servicePath)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> {
                        try {
                            String filename = path.getFileName().toString();
                            String relativePath = getRelativePath(serviceName,
                                    path.getParent().toString().replace(properties.getBasePath() + "/" + serviceName + "/", ""),
                                    filename);

                            return AttachmentDTO.builder()
                                    .filename(filename)
                                    .fileSize(Files.size(path))
                                    .contentType(Files.probeContentType(path))
                                    .filePath(relativePath)
                                    .downloadUrl(buildDownloadUrl(relativePath))
                                    .build();
                        } catch (IOException e) {
                            log.error("Error reading file: {}", path, e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            throw new AttachmentException("Failed to list files for service: " + serviceName, e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new AttachmentException("Cannot store empty file");
        }

        if (file.getSize() > properties.getMaxFileSize()) {
            throw new AttachmentException("File size exceeds the maximum allowed size of " +
                    (properties.getMaxFileSize() / (1024 * 1024)) + "MB");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = "." + FilenameUtils.getExtension(filename).toLowerCase();

        boolean isAllowed = false;
        for (String allowedExt : properties.getAllowedExtensions()) {
            if (allowedExt.equalsIgnoreCase(extension)) {
                isAllowed = true;
                break;
            }
        }

        if (!isAllowed) {
            throw new AttachmentException("File type not allowed. Allowed types: " +
                    String.join(", ", properties.getAllowedExtensions()));
        }
    }

    private Path getTargetLocation(String serviceName, String datePath) {
        return Paths.get(properties.getBasePath())
                .resolve(serviceName)
                .resolve(datePath)
                .normalize();
    }

    private String getRelativePath(String serviceName, String datePath, String filename) {
        return serviceName + "/" + datePath + "/" + filename;
    }

    private String buildDownloadUrl(String relativePath) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(Constants.API_URI_V1 + "/attachments/download/")
                .path(relativePath)
                .toUriString();
    }
}

package com.podcast.podcast.repository;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "attachment")
@Component
public class AttachmentProperties {
    private String basePath;
    private long maxFileSize;
    private String[] allowedExtensions;
}
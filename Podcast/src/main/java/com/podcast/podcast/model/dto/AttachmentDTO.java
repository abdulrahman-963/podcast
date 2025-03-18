package com.podcast.podcast.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachmentDTO {

    private String filename;
    private String downloadUrl;
    private String contentType;
    private Long fileSize;
    private String filePath;
}

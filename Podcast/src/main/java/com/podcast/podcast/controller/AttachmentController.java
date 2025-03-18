package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.model.dto.AttachmentDTO;
import com.podcast.podcast.service.AttachmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(Constants.API_URI_V1 + "/attachments")
@RequiredArgsConstructor
@Slf4j
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("serviceName") String serviceName) {

        log.info("Received upload request for service: {}, file: {}", serviceName, file.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.storeFile(file, serviceName));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam String filePath,
            HttpServletRequest request) {

        log.info("Received download request for file: {}", filePath);

        Resource resource = attachmentService.loadFileAsResource(filePath);

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.warn("Could not determine file type, using application/octet-stream");
            contentType = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(
            @RequestParam String filePath) {

        log.info("Received delete request for file: {}", filePath);

        attachmentService.deleteFile(filePath);


        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list/{serviceName}")
    public ResponseEntity<List<AttachmentDTO>> listFiles(@PathVariable String serviceName) {
        log.info("Received list request for service: {}", serviceName);

        List<AttachmentDTO> response = attachmentService.listFiles(serviceName);

        return ResponseEntity.ok(response);
    }

}



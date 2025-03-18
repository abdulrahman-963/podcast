package com.podcast.podcast.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Set;

@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Set<String> EXCLUDED_PATHS = Set.of(
            "/swagger-ui", "/v3/api-docs", "/actuator", "/api/v1/attachments/download"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return EXCLUDED_PATHS.stream().anyMatch(request.getRequestURI()::startsWith);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Instant startTime = Instant.now();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            Instant endTime = Instant.now();
            logRequest(wrappedRequest);
            logResponse(wrappedResponse, Duration.between(startTime, endTime).toMillis());
            wrappedResponse.copyBodyToResponse(); // Important: Copy response body back to original response
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder logMessage = new StringBuilder("\n===== Incoming Request =====\n")
                .append("Method: ").append(request.getMethod()).append("\n")
                .append("URI: ").append(request.getRequestURI()).append("\n")
                .append("LocalAddress: ").append(request.getLocalAddr()).append("\n")
                .append("RemoteAddress: ").append(request.getRemoteAddr()).append("\n")
                .append("Query Params: ").append(request.getQueryString() != null ? request.getQueryString() : "None").append("\n")
                .append("Headers: ").append(getHeadersAsString(request)).append("\n")
                .append("Body: ").append(getBody(request.getContentAsByteArray())).append("\n");

        log.info(logMessage.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {
        StringBuilder logMessage = new StringBuilder("\n===== Outgoing Response =====\n")
                .append("Status: ").append(response.getStatus()).append("\n")
                .append("Headers: ").append(getHeadersAsString(response)).append("\n")
                .append("Response Time: ").append(duration).append(" ms\n")
                .append("Body: ").append(getBody(response.getContentAsByteArray())).append("\n");

        log.info(logMessage.toString());
    }

    private String getHeadersAsString(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.append(header).append(": ").append(request.getHeader(header)).append(", ");
        }
        return headers.length() > 2 ? headers.substring(0, headers.length() - 2) : "None";
    }

    private String getHeadersAsString(HttpServletResponse response) {
        StringBuilder headers = new StringBuilder();
        response.getHeaderNames().forEach(header ->
                headers.append(header).append(": ").append(response.getHeader(header)).append(", "));
        return headers.length() > 2 ? headers.substring(0, headers.length() - 2) : "None";
    }

    private String getBody(byte[] content) {
        return content.length > 0 ? new String(content, StandardCharsets.UTF_8) : "None";
    }


}

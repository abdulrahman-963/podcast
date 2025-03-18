package com.podcast.podcast.exception;


import com.podcast.podcast.enums.ErrorCode;
import com.podcast.podcast.micrometers.ErrorMetricsRecorder;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_LOG_VAR = "correlationId";

    private final MessageSource messageSource;
    private final ErrorMetricsRecorder metricsRecorder;


    // Enhanced validation handler with field details
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        BindingResult result = ex.getBindingResult();
        List<ValidationError> validationErrors = result.getFieldErrors()
                .stream()
                .map(this::createValidationError)
                .toList();

        ErrorResponse response = buildErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR, request)
                .validationErrors(validationErrors)
                .build();

        metricsRecorder.recordError(ErrorCode.VALIDATION_ERROR);
        logError(ex, request);
        return ResponseEntity.badRequest().body(response);
    }

    // Handle custom business exceptions
    @ExceptionHandler(GeneralBusinessException.class)
    public ResponseEntity<ErrorResponse> handleGeneralBusinessException(
            GeneralBusinessException ex, WebRequest request) {

        ErrorResponse response = buildErrorResponse(ex.getStatus(),
                ex.getErrorCode(), request)
                .message(resolveLocalizedMessage(ex, request))
                .build();

        metricsRecorder.recordError(ex.getErrorCode());
        logError(ex, request);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    // Handle database constraint violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        ErrorResponse response = buildErrorResponse(HttpStatus.CONFLICT,
                ErrorCode.DATA_INTEGRITY_VIOLATION, request)
                .message(resolveLocalizedMessage("error.data.integrity", request))
                .build();

        metricsRecorder.recordError(ErrorCode.DATA_INTEGRITY_VIOLATION);
        logError(ex, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Handle entity not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {

        ErrorResponse response = buildErrorResponse(HttpStatus.NOT_FOUND,
                ErrorCode.RESOURCE_NOT_FOUND, request)
                .message(ex.getMessage())
                .build();

        metricsRecorder.recordError(ErrorCode.RESOURCE_NOT_FOUND);
        logError(ex, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle timeouts
    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ErrorResponse> handleTimeoutException(
            TimeoutException ex, WebRequest request) {

        ErrorResponse response = buildErrorResponse(HttpStatus.GATEWAY_TIMEOUT,
                ErrorCode.TIMEOUT_ERROR, request)
                .message(resolveLocalizedMessage("error.timeout", request))
                .build();

        metricsRecorder.recordError(ErrorCode.TIMEOUT_ERROR);
        logError(ex, request);
        return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
    }

    // Handle rate limiting
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<ErrorResponse> handleRateLimitExceeded(
            RequestNotPermitted ex, WebRequest request) {

        ErrorResponse response = buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS,
                ErrorCode.RATE_LIMIT_EXCEEDED, request)
                .message(resolveLocalizedMessage("error.rate.limit", request))
                .build();

        metricsRecorder.recordError(ErrorCode.RATE_LIMIT_EXCEEDED);
        logError(ex, request);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("Rate limit exceeded. Try again later.")
                .body(response);
    }

    // Enhanced 404 handler
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ErrorResponse response = buildErrorResponse(HttpStatus.NOT_FOUND,
                ErrorCode.RESOURCE_NOT_FOUND, request)
                .message(resolveLocalizedMessage("error.resource.not.found", request))
                .build();

        metricsRecorder.recordError(ErrorCode.RESOURCE_NOT_FOUND);
        logError(ex, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Content negotiation support
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ErrorResponse response = buildErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ErrorCode.UNSUPPORTED_MEDIA_TYPE, request)
                .message(resolveLocalizedMessage("error.media.unsupported", request))
                .supportedMediaTypes(ex.getSupportedMediaTypes())
                .build();

        metricsRecorder.recordError(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        logError(ex, request);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(response);
    }

    @ExceptionHandler(AttachmentException.class)
    public ResponseEntity<ErrorResponse> handleAttachmentException(AttachmentException ex, WebRequest request) {
        log.error("Attachment operation failed", ex);

        ErrorResponse response = buildErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.ATTACHMENT_UPLOAD_ERROR, request)
                .message(resolveLocalizedMessage("Attachment operation failed", request))
                .build();
        return ResponseEntity.badRequest().body(response);
    }


    // Unified error response builder
    private ErrorResponse.ErrorResponseBuilder buildErrorResponse(
            HttpStatus status, ErrorCode errorCode, WebRequest request) {

        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .errorCode(errorCode)
                .correlationId(getCorrelationId(request))
                .instance(request.getContextPath() + request.getDescription(false).replace("uri=", ""));
    }

    // Correlation ID management
    private String getCorrelationId(WebRequest request) {
        String correlationId = MDC.get(CORRELATION_ID_LOG_VAR);
        if (correlationId == null) {
            correlationId = request.getHeader(CORRELATION_ID_HEADER);
            if (correlationId == null) {
                correlationId = UUID.randomUUID().toString();
            }
            MDC.put(CORRELATION_ID_LOG_VAR, correlationId);
        }
        return correlationId;
    }

    // Localized message resolution
    private String resolveLocalizedMessage(String messageKey, WebRequest request) {
        Locale locale = request.getLocale();
        return messageSource.getMessage(messageKey, new String[]{"as"}, messageKey, locale);
    }

    private String resolveLocalizedMessage(GeneralBusinessException ex, WebRequest request) {
        return messageSource.getMessage(ex.getMessageKey(), ex.getArgs(),
                ex.getDefaultMessage(), request.getLocale());
    }

    // Structured logging
    private void logError(Exception ex, WebRequest request) {
        log.error("Error [Correlation-ID: {}] - {}: {}",
                getCorrelationId(request),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex);
    }

    // Validation error details
    private ValidationError createValidationError(FieldError fieldError) {
        return ValidationError.builder()
                .field(fieldError.getField())
                .rejectedValue(fieldError.getRejectedValue())
                .message(fieldError.getDefaultMessage())
                .build();
    }

    // Error Response DTO
    @Builder
    public record ErrorResponse(
            Instant timestamp,
            int status,
            String error,
            ErrorCode errorCode,
            String correlationId,
            String instance,
            String message,
            List<ValidationError> validationErrors,
            List<MediaType> supportedMediaTypes,
            Integer retryAfter
    ) {
    }

    // Validation Error DTO
    @Builder
    public record ValidationError(
            String field,
            Object rejectedValue,
            String message
    ) {
    }


}
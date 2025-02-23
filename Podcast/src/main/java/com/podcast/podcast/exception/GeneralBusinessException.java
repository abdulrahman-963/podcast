package com.podcast.podcast.exception;

import com.podcast.podcast.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralBusinessException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorCode errorCode;
    private final String messageKey;
    private final Object[] args;
    private final String defaultMessage;

    public GeneralBusinessException(HttpStatus status, ErrorCode errorCode,
                                    String messageKey, String defaultMessage,
                                    Object... args) {
        super(defaultMessage);  // Pass to parent constructor
        this.status = status;
        this.errorCode = errorCode;
        this.messageKey = messageKey;
        this.defaultMessage = defaultMessage;
        this.args = args;
    }


}

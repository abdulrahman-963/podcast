package com.podcast.podcast.micrometers;

import com.podcast.podcast.enums.ErrorCode;

// Metrics Interface
public interface ErrorMetricsRecorder {
    void recordError(ErrorCode errorCode);
}
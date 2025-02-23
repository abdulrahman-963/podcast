package com.podcast.podcast.micrometers;

import com.podcast.podcast.enums.ErrorCode;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MicrometerMetricsRecorder implements ErrorMetricsRecorder {

    private final MeterRegistry meterRegistry;

    @Override
    public void recordError(ErrorCode errorCode) {
        Counter.builder("api.errors")
                .tag("errorCode", errorCode.name())
                .register(meterRegistry)
                .increment();
    }
}

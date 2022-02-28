package com.example.demo.enhancer;

import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.LoggingEnhancer;

public class GcpEnhancer implements LoggingEnhancer {
    @Override
    public void enhanceLogEntry(LogEntry.Builder builder) {
        builder.addLabel("test-label","enhanced label");
    }
}

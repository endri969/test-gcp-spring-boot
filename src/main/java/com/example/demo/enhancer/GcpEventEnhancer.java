package com.example.demo.enhancer;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Payload;
import com.google.cloud.logging.logback.LoggingEventEnhancer;
import org.slf4j.Marker;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class GcpEventEnhancer implements LoggingEventEnhancer {
    @Override
    public void enhanceLogEntry(LogEntry.Builder builder, ILoggingEvent iLoggingEvent) {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
        ObjectNode jsonNodes = buildJsonObject(iLoggingEvent, objectMapper);
        Payload.JsonPayload jsonPayload = Payload.JsonPayload.of(objectMapper.convertValue(jsonNodes, new TypeReference<Map<String, Object>>() {
        }));

        builder.setPayload(jsonPayload);
    }

    private ObjectNode buildJsonObject(ILoggingEvent event, ObjectMapper mapper) {

        ObjectNode json = mapper.createObjectNode();
        json.put("createDateTime", LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()), ZoneId.systemDefault()).toString());
        json.put("level", event.getLevel().toString());
        json.put("logger", event.getLoggerName());

        Marker marker = event.getMarker();
        json.put("marker", marker.getName());

        return json;
    }
}

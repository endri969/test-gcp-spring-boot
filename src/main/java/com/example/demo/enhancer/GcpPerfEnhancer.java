package com.example.demo.enhancer;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Payload;
import com.google.cloud.logging.logback.LoggingEventEnhancer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Iterator;
import java.util.Map;

public class GcpPerfEnhancer implements LoggingEventEnhancer {
    @Override
    public void enhanceLogEntry(LogEntry.Builder builder, ILoggingEvent iLoggingEvent) {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();

        try {
            Map<String, Object> mapObject
                    = objectMapper.readValue(iLoggingEvent.getFormattedMessage(), new TypeReference<>() {
            });

            Payload.JsonPayload jsonPayload = Payload.JsonPayload.of(mapObject);

            builder.setPayload(jsonPayload);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        Iterator var3 = iLoggingEvent.getMDCPropertyMap().entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var3.next();
            if (null != entry.getKey() && null != entry.getValue()) {
                builder.addLabel((String)entry.getKey(), (String)entry.getValue());
            }
        }
    }

}

package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private static final Logger perfLogger = LoggerFactory.getLogger("performance");

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();

    class Sample{
        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }

    @GetMapping("/")
    Sample hello() throws JsonProcessingException {
        Sample sample = new Sample();
        sample.setId("1234567");
        logger.info(MarkerFactory.getMarker("JSON_PAYLOAD"),mapper.writeValueAsString(sample));
        perfLogger.info(mapper.writeValueAsString(sample));
        return sample;
    }

    @GetMapping("/{id}")
    Sample helloPathVariable(@PathVariable String id) throws JsonProcessingException {
        Sample sample = new Sample();
        sample.setId(id);
        logger.info(MarkerFactory.getMarker("JSON_PAYLOAD"),mapper.writeValueAsString(sample));
        perfLogger.info(mapper.writeValueAsString(sample));

        return sample;
    }

}

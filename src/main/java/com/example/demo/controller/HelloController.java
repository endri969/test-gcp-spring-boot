package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

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

        logger.info(mapper.writeValueAsString(sample));

        return sample;
    }

}

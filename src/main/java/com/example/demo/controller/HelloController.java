package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.secretmanager.SecretManagerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private static final Logger perfLogger = LoggerFactory.getLogger("performance");

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();

    private final Environment environment;

    private final SecretManagerTemplate secretManagerTemplate;

    @Value("${sm://database-password}")
    private String password;

    public HelloController(Environment environment, SecretManagerTemplate secretManagerTemplate) throws JsonProcessingException {
        this.environment = environment;
        this.secretManagerTemplate = secretManagerTemplate;
    }

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

    @GetMapping("/environment")
    String[] getProfiles(){
        return environment.getActiveProfiles();
    }

    @GetMapping("/secret")
    String getSecret(){
        return secretManagerTemplate.getSecretString("my-secret");
    }

    @GetMapping("/password")
    String getPassword(){
        return secretManagerTemplate.getSecretString("database-password");
    }

}

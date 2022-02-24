package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
@Component
@Order(0)
class AppCdsApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    private final boolean appcds;
    private final ApplicationContext ctx;
    private final RestTemplate restTemplate = new RestTemplate();

    AppCdsApplicationListener(@Value("${appcds:false}") boolean appcds,
                              ApplicationContext ctx) {
        this.appcds = appcds;
        this.ctx = ctx;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (appcds) {
            restTemplate.getForEntity("http://localhost:8080/", String.class);
            SpringApplication.exit(ctx, () -> 0);
        }
    }
}
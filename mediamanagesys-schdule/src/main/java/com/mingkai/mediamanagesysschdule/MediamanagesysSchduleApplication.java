package com.mingkai.mediamanagesysschdule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.mingkai")
@EnableSwagger2
@EnableScheduling
public class MediamanagesysSchduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysSchduleApplication.class, args);
    }

}

package com.mingkai.mediamanagesysportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.mingkai")
@EnableSwagger2
public class MediamanagesysPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysPortalApplication.class, args);
    }

}

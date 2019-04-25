package com.mingkai.mediamanagesysbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.mingkai")
@EnableSwagger2
public class MediamanagesysBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysBackendApplication.class, args);
    }

}

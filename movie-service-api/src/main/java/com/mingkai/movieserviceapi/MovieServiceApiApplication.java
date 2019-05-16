package com.mingkai.movieserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "com.mingkai")
@EnableEurekaServer
public class MovieServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieServiceApiApplication.class, args);
    }

}

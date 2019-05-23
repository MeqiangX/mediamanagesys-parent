package com.mingkai.screenserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "com.mingkai")
@EnableEurekaServer
public class ScreenServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreenServiceApiApplication.class, args);
    }

}

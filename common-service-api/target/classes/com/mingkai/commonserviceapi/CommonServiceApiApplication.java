package com.mingkai.commonserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "com.mingkai")
@EnableEurekaServer
public class CommonServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApiApplication.class, args);
    }

}

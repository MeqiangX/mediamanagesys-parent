package com.mingkai.mediamanagesyscommonapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class MediamanagesysCommonAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysCommonAppApplication.class, args);
    }

}

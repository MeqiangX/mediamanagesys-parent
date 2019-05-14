package com.meqiang.payserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaServer  // 注册服务
@EnableEurekaClient // 调用服务
@EnableFeignClients
@EnableDiscoveryClient
public class PayServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayServiceApiApplication.class, args);
    }

}

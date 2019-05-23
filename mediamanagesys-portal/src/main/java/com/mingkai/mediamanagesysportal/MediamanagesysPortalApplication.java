package com.mingkai.mediamanagesysportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.mingkai.mediamanagesysmapper","com.mingkai.mediamanagesysportal"})
@EnableSwagger2
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
/*@EnableSwagger2*/
public class MediamanagesysPortalApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysPortalApplication.class, args);
    }


    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}

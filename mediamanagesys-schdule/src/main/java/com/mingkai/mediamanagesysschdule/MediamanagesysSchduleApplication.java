package com.mingkai.mediamanagesysschdule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.mingkai")
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@EnableSwagger2
@EnableScheduling
public class MediamanagesysSchduleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysSchduleApplication.class, args);
    }


    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}

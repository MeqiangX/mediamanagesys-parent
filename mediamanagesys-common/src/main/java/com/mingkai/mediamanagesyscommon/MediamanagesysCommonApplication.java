package com.mingkai.mediamanagesyscommon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mingkai.mediamanagesyscommon"})
public class MediamanagesysCommonApplication{

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysCommonApplication.class, args);
    }


   /* @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/

}

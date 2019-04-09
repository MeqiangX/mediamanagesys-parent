package com.mingkai.mediamanagesyscommon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.mingkai.mediamanagesyscommon.mapper"})
public class MediamanagesysCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediamanagesysCommonApplication.class, args);
    }

}

package com.mingkai.mediamanagesysschdule.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2  //swagger2 启动注解
public class SwaggerScheduleConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())  //构建swagger-ui 基本信息
                .select() //返回一个ApiSelectorBuilder实例来控制哪些接口暴露给swagger来展现
                .apis(RequestHandlerSelectors.basePackage("com.mingkai.mediamanagesysschdule.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    // 扫描basePackage包下的所有除了@ApiIgnore 修饰的Controller 给Swagger 来展现

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("定时任务RestFul Apis")
                .description("整合测试 调试接口")
                .termsOfServiceUrl("www.baidu.com")
                .contact("Meqiang")
                .version("1.0.0")
                .build();
    }

}

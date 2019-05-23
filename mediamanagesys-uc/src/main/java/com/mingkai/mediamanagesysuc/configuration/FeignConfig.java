package com.mingkai.mediamanagesysuc.configuration;

import com.google.common.base.Strings;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-21 17:03
 */
@Configuration
public class FeignConfig {

    /**
     * 将cookie 请求头中的sessionid 传递到 服务层
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            if (Strings.isNullOrEmpty(sessionId)){
                requestTemplate.header("Cookie","SESSION=" + sessionId);
            }
        };
    }

}

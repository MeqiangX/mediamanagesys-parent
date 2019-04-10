package com.mingkai.mediamanagesysuc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-10 17:19
 */
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    public static class WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable(); //关闭scrf防护
            http.authorizeRequests()
                    .antMatchers("/index")
                    .hasRole("ADMIN")
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic();

            //设置注销时访问的Url

            http.logout().logoutSuccessUrl("/login");
        }
    }

}

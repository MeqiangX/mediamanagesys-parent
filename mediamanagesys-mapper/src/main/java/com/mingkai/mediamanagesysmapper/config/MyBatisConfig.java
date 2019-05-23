package com.mingkai.mediamanagesysmapper.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Date;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-04 16:59
*/

@Configuration
@MapperScan("com.mingkai.mediamanagesysmapper.mapper")
public class MyBatisConfig {



    /**
     * 配置逻辑删除
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector(){
        LogicSqlInjector logicSqlInjector = new LogicSqlInjector();
        return logicSqlInjector;
    }



    /**
     * 元对象字段填充控制器
     * @return
     */
    @Bean
    public DTMetaObjectHandler metaObjectHandler() {
        return new DTMetaObjectHandler();
    }


    /**
     * 元对象字段填充控制器
     */
    class DTMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            this.setFieldValByName("creator","yunfeng",metaObject);
            this.setFieldValByName("updator", "yunfeng",metaObject);
            this.setFieldValByName("isDeleted",0,metaObject);
            this.setFieldValByName("createTime",new Date(),metaObject);
            this.setFieldValByName("updateTime",new Date(),metaObject);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName("updator","yunfeng-update",metaObject);
            this.setFieldValByName("updateTime",new Date(),metaObject);
        }
    }



    /**
     * MybatisPlus 的 分页拦截器
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        System.out.println("执行");
        return new PaginationInterceptor();
    }



    /**
     * Sql执行效率插件 打印sql 语句
     * @return
     */
    @Bean
    @Profile({"dev","test"})
    public PerformanceInterceptor performanceInterceptor(){

        // sql 执行效率插件 打印sql
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();

        // 参数 maxTime 执行最大时间 超过自动停止

        // format 是否格式化sql  默认false
        performanceInterceptor.setFormat(Boolean.FALSE);

        System.out.println("初始化");

        return performanceInterceptor;
    }

}

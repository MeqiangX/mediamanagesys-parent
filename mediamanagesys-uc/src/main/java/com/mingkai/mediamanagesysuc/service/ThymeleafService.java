package com.mingkai.mediamanagesysuc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

/**
 * @description: 模板引擎渲染
 * @author: Created by 云风 on 2019-04-01 11:40
 */
@Service
public class ThymeleafService {


    @Autowired
    private SpringTemplateEngine templateEngine;

    /**
     * 渲染thymeleaf
     * @param template
     * @param params
     * @return
     */
    public String render(String template, Map<String,Object> params){

        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariables(params);

        return templateEngine.process(template,context);

    }

}

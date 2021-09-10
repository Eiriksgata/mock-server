package com.github.eiriksgata.mockserver.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.ajb.pushserver.conf
 * date: 2020/12/10
 **/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /*
     * 文件默认存储位置
     */
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "\\hik_ipc_pic";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**").addResourceLocations("file:\\" + DEFAULT_FILE_PATH + "\\\\");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registration) {
        registration.addInterceptor(new RequestInterceptor());
    }

}

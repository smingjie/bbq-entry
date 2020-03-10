package com.micro.bbqentry.config;

import com.micro.bbqentry.general.annotation.ResponseFormatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web app 配置类
 *
 * @author jockeys
 * @since 2020/3/10
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    ResponseFormatInterceptor responseFormatInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(responseFormatInterceptor).addPathPatterns("/**");
    }
}
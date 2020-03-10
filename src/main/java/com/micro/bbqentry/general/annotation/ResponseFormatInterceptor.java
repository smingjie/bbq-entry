package com.micro.bbqentry.general.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 响应格式-自定义注解拦截处理
 *
 * @author jockeys
 * @since 2020/3/6
 */
@Slf4j
@Component
public class ResponseFormatInterceptor implements HandlerInterceptor {
    public static final String RESPONSE_FORMAT_FLAG = "RESPONSE_JSON_FLAG";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求里面添加注解@ResponseFormat的标志，为了返回响应时拦截进行处理
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(ResponseFormat.class)) {
                request.setAttribute(RESPONSE_FORMAT_FLAG, clazz.getAnnotation(ResponseFormat.class));
            } else if (method.isAnnotationPresent(ResponseFormat.class)) {
                request.setAttribute(RESPONSE_FORMAT_FLAG, method.getAnnotation(ResponseFormat.class));
            }
        }
        return true;
    }

}

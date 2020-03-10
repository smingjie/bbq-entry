package com.micro.bbqentry.general.annotation;

import com.micro.bbqentry.general.common.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jockeys
 * @since 2020/3/6
 */
@Slf4j
@ControllerAdvice
public class ResponseFormatHandler implements ResponseBodyAdvice<Object> {
    /**
     * 标记位
     */
    public static final String RESPONSE_FORMAT_FLAG = "RESPONSE_JSON_FLAG";


    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {

        // 判断请求是否包含了注解标记
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        ResponseFormat responseFlag = (ResponseFormat) request.getAttribute(RESPONSE_FORMAT_FLAG);
        return null != responseFlag;
        //return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        log.info("进入返回体重写过程...");
        if (null == o) {
            //成功执行但无数据返回，返回code，msg
            return ResponseJson.ok();
        } else if (o instanceof ResponseJson) {
            //已经在controller封装完成，直接返回
            return o;
        }
        // 尚未包装的成功数据，此处封装code msg data
        return ResponseJson.ok(o);
    }
}

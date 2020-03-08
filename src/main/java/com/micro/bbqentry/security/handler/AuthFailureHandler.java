package com.micro.bbqentry.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.common.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证(失败)处理器
 * 需要注入到 AuthenticationProvider 生效，当认证失败时才会调用该实现
 *
 * @author jockeys
 * @since 2020/3/8
 */
@Slf4j
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("进入AuthFailureHandler处理器，执行验证失败处理过程...");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseJson data = ResponseJson.error(ResponseEnum.USER_AUTH_FAIL.getCode(), exception.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(data));
    }
}

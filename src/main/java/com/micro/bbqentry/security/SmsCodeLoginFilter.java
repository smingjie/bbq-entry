package com.micro.bbqentry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.model.param.SmsCodeLoginParam;
import com.micro.bbqentry.security.handler.AuthFailureHandler;
import com.micro.bbqentry.security.handler.AuthSuccessHandler;
import com.micro.bbqentry.security.model.PhoneCodeAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jockeys
 * @since 2020/3/9
 */
public class SmsCodeLoginFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    private SmsCodeLoginFilter() {
        super(new AntPathRequestMatcher("/smsLogin", "POST"));
    }

    /**
     * 构造器 只开放一个
     *
     * @param authenticationManager 鉴权管理器
     */
    public SmsCodeLoginFilter(AuthenticationManager authenticationManager) {
        this();
        // 必须 默认设置认证成功处理器、认证失败处理器
        this.setAuthenticationSuccessHandler(new AuthSuccessHandler());
        this.setAuthenticationFailureHandler(new AuthFailureHandler());
        // 构造器注入
        this.setAuthenticationManager(authenticationManager);
    }
    /**
     * 接收并解析用户凭证,进行认证尝试
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            SmsCodeLoginParam smsCode = new ObjectMapper().readValue(request.getInputStream(), SmsCodeLoginParam.class);
            logger.info("执行认证处理过程,获取到登录参数：{} " + smsCode);
            //登录时authorities现在是空的，登录校验成功后，会把权限写入token返回给前端，
            AbstractAuthenticationToken authInfo = new PhoneCodeAuthenticationToken(
                    smsCode.getPhone(), smsCode.getCode());
            return getAuthenticationManager().authenticate(authInfo);
        } catch (Exception e) {
            throw new AuthenticationServiceException(ResponseEnum.SERVER_ERROR.getMessage());
        }
    }

}

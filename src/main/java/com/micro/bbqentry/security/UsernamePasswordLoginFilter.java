package com.micro.bbqentry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.exception.BusinessException;
import com.micro.bbqentry.model.param.PasswordLoginParam;
import com.micro.bbqentry.security.handler.AuthFailureHandler;
import com.micro.bbqentry.security.handler.AuthSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 登录过滤器 {UsernamePasswordLoginFilter} 继承自UsernamePasswordAuthenticationFilter
 * 主要与2点内容：
 * 1 默认注入了自定义的认证成功处理器(AuthSuccessHandler)，认证失败处理器(AuthFailureHandler)
 * 2 重写了其中的一个方法(attemptAuthentication)：接收并解析用户凭证，进行认证尝试
 *
 * @author jockeys
 * @since 2020/2/5
 */
@Slf4j
public class UsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 构造器 只开放一个
     *
     * @param authenticationManager 鉴权管理器
     */
    public UsernamePasswordLoginFilter(AuthenticationManager authenticationManager) {
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
        try {
            PasswordLoginParam user = new ObjectMapper().readValue(request.getInputStream(), PasswordLoginParam.class);
            logger.info("执行认证处理过程,获取到登录参数：{} " + user);
            //登录时authorities现在是空的，登录校验成功后，会把权限写入token返回给前端，
            AbstractAuthenticationToken authInfo = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), new ArrayList<>());
            return getAuthenticationManager().authenticate(authInfo);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthenticationServiceException(ResponseEnum.SERVER_ERROR.getMessage());
        }
    }


}
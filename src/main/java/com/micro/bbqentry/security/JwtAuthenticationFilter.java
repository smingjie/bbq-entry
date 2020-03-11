package com.micro.bbqentry.security;

import com.micro.bbqentry.general.exception.BusinessException;
import com.micro.bbqentry.general.utils.JwtUtils;
import com.micro.bbqentry.security.model.AuthenticationTokenJwt;
import com.micro.bbqentry.security.model.MyUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 自定义JWT认证过滤器
 * 该类继承自 BaseLoginFilter，在 doFilterInternal 方法中，
 * 从 http 头的 Authorization 项读取token数据，然后用自定义的 JwtUtility工具类提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author jockeys
 * @since 2020/2/5
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    /**
     * header中的名称
     */
    private static final String HEADER = "Authorization";
    /**
     * 前缀 注意空格
     */
    private static final String HEADER_START_WITH = "Bearer ";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(HEADER);
        // 如果请求头中没有Authorization信息,则直接放行
        if (tokenHeader == null || !tokenHeader.startsWith(HEADER_START_WITH)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        UserDetails principal = getUserDetails(tokenHeader);
        AuthenticationTokenJwt authentication = new AuthenticationTokenJwt(principal, request, null);
        // 放到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * 这里从请求头的中截取有效token，并获取用户信息
     */
    private UserDetails getUserDetails(String tokenHeader) {
        try {
            String token = tokenHeader.replace(HEADER_START_WITH, "");
            //解析用户信息
            Map resultMap = JwtUtils.parseToken(token);
            logger.info("解析用户信息为 {}" + resultMap);
            if (resultMap != null && !resultMap.isEmpty()) {
                return MyUser.phaseByMap(resultMap);
            }
        } catch (BusinessException e) {
            throw new AuthenticationServiceException(e.getMessage());
        } catch (Exception e) {
            throw new AuthenticationServiceException("认证服务异常");
        }
        return null;
    }
}

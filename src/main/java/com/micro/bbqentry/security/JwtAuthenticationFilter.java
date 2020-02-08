package com.micro.bbqentry.security;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.exception.BusinessException;
import com.micro.bbqentry.general.utils.JwtUtility;
import com.micro.bbqentry.security.under.MyUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 自定义JWT认证过滤器
 * 该类继承自 BasicAuthenticationFilter，在 doFilterInternal 方法中，
 * 从 http 头的 Authorization 项读取token数据，然后用自定义的 JwtUtility工具类提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author jockeys
 * @since 2020/2/5
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    //header中的名称
    private final String HEADER = "Authorization";
    //前缀 注意空格
    private final String HEADER_STARTWHTH = "Bearer ";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(HEADER);
        // 如果请求头中没有Authorization信息,则直接放行
        if (tokenHeader == null || !tokenHeader.startsWith(HEADER_STARTWHTH)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        chain.doFilter(request, response);
    }

    /**
     * 这里从请求头的中截取有效token，并获取用户信息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        try {
            String token = tokenHeader.replace(HEADER_STARTWHTH, "");
            //解析用户信息
            Map resultMap = JwtUtility.parseToken(token);
            logger.info("解析用户信息为 {}"+resultMap);
            if (resultMap != null && resultMap.size() != 0) {
                return new UsernamePasswordAuthenticationToken(MyUser.phaseByMap(resultMap), null, new ArrayList<>());
            }
        } catch (TokenExpiredException e) {
            logger.error("Token已过期: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_CREATE_ERRPR);
        } catch (JWTCreationException e) {
            logger.error("Token没有被正确构造: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_CREATE_ERRPR);
        } catch (SignatureVerificationException e) {
            logger.error("签名校验失败: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_SIGN_ERROR);
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            throw new BusinessException(ResponseEnum.TOKEN_SIGN_ERROR);
        }
        return null;
    }
}

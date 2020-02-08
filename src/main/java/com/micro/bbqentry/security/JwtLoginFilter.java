package com.micro.bbqentry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.general.exception.BusinessException;
import com.micro.bbqentry.general.utils.JwtUtility;
import com.micro.bbqentry.model.param.LoginVO;
import com.micro.bbqentry.security.under.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 登录过滤器 {JwtLoginFilter}功能描述：
 * 在验证用户名密码正确后，生成一个token，并将token返回给客户端。
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法：
 * attemptAuthentication ：接收并解析用户凭证,
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token.
 *
 * @author jockeys
 * @since 2020/2/5
 */
@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 接收并解析用户凭证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginVO user = new ObjectMapper().readValue(req.getInputStream(), LoginVO.class);
            logger.info("获取到登录参数：{} " + user);
            //登录时authorities现在是空的，登录校验成功后，会把权限写入token返回给前端，
            //前端访问接口时会带上token，权限校验时会解析token得到具体的权限
            Authentication authentication = null;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword(),
                                new ArrayList<>())
                );
            } catch (AuthenticationException e) {
                //验证失败则返回验证失败的Json串{code，msg}
                res.setHeader("Content-Type","application/json;charset=utf-8");
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                ResponseJson data = ResponseJson.error(ResponseEnum.USER_AUTH_FAIL.getCode(), e.getMessage());
                res.getWriter().write(new ObjectMapper().writeValueAsString(data));

            }
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        try {
            // 获取用户信息 MyUser
            MyUser user = (MyUser) authResult.getPrincipal();

            // 转换为map形式，用来生成token
            String token = JwtUtility.createToken(user.asMap());
            logger.info("生成的token：{} " + token);

            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            }
        }
    }
}
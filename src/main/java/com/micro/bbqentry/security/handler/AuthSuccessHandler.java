package com.micro.bbqentry.security.handler;

import com.micro.bbqentry.general.exception.BusinessException;
import com.micro.bbqentry.general.utils.JwtUtils;
import com.micro.bbqentry.security.model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证(成功)处理器
 * 需要注入到 AuthenticationProvider 生效，当认证成功时才会调用该实现
 * 我们在这个实现类中创建token
 *
 * @author jockeys
 * @since 2020/3/8
 */
@Slf4j
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private static final String HEADER_AUTH_NAME = "Authorization";
    private static final String HEADER_AUTH_PREFIX = "Bearer ";

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.error("进入AuthSuccessHandler处理器，执行验证成功处理过程...");
        try {
            log.info("通过Authentication对象获取信息...");
            // 获取用户信息 MyUser
            MyUser user = (MyUser) authentication.getPrincipal();
            // 转换为map形式，用来生成token
            String token = JwtUtils.createToken(user.asMap());
            // 登录成功后，返回token到header里面
            response.addHeader(HEADER_AUTH_NAME, HEADER_AUTH_PREFIX + token);
            log.info("Token令牌(带前缀)生成结果:{}", response.getHeader(HEADER_AUTH_NAME));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}

package com.micro.bbqentry.security.provider;


import com.micro.bbqentry.security.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 身份认证验证组件(账号密码方式)，可拆解为2个任务：（抽取到LoginService中去实现）
 * 1获取信息：通常是数据层的操作，从其他数据源（如mysql/redis/ldap等）获取待认证的数据，
 * 2验证信息：表现为验证算法选取，外部数据与获取的数据做校验，需要加密规则 PasswordEncoder
 *
 * @author jockeys
 * @since 2020/2/6
 */
@Slf4j
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private LoginService loginService;

    /**
     * 构造器 注入
     */
    public UsernamePasswordAuthenticationProvider(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 真实的验证过程，针对于该AuthenticationProvider特别重要
     *
     * @param authentication 外部感知的输入信息
     * @return 验证成功再次封装的AuthenticationException对象
     * @throws AuthenticationException 认证失败异常抽象类，不能实例化
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return loginService.loginByUsernamePassword((UsernamePasswordAuthenticationToken) authentication);
    }


    /**
     * 是否可以提供输入类型的认证服务
     *
     * @param authentication 认证实体类
     * @return 支持返回true，否则返回false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
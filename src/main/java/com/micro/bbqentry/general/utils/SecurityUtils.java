package com.micro.bbqentry.general.utils;

import com.micro.bbqentry.security.under.MyUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户安全上下文，用来获取线程内有效的用户信息
 *
 * @author jockeys
 * @since 2020/2/6
 */
@Component
public class SecurityUtils {
    /**
     * 获取用户信息
     *
     * @return MyUser
     */
    public static MyUser getCurrUser() {
        /**
         SecurityContextHolder.getContext()获取安全上下文对象，就是那个保存在 ThreadLocal 里面的安全上下文对象
         总是不为null(如果不存在，则创建一个authentication属性为null的empty安全上下文对象)
         获取当前认证了的 principal(当事人),或者 request token (令牌)
         如果没有认证，会是 null,该例子是认证之后的情况
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return (MyUser) authentication.getPrincipal();
            }
        }
        return null;
    }
}

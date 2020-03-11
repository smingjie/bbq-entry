package com.micro.bbqentry.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 标识 spring security上下文中的Authentication对象封装
 * 必须有认证主体 principal
 *
 * @author jockeys
 * @since 2020/3/10
 */
public class AuthenticationTokenJwt extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -2080205818255036675L;
    /**
     * 认证主体
     */
    private Object principal;

    /**
     * 构造函数
     *
     * @param principal   认证主体信息
     * @param details     系统相关，如ip，session等
     * @param authorities 授权信息
     */
    public AuthenticationTokenJwt(Object principal, Object details, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.setDetails(details);
        this.principal = principal;
        this.setAuthenticated(true);
    }

    /**
     * 构造函数
     *
     * @param principal 认证主体信息
     */
    public AuthenticationTokenJwt(Object principal, Object details) {
        this(principal, details, null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}

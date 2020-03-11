package com.micro.bbqentry.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机短信AuthenticationProvider需要的Authentication对象封装
 *
 * @author jockeys
 * @since 2020/3/8
 */
public class AuthenticationTokenSmsCode extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -6514553342446135299L;
    /**
     * 认证主体
     */
    private Object principal;
    /**
     * 认证凭证
     */
    private Object credentials;

    public AuthenticationTokenSmsCode(String principal, String credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public AuthenticationTokenSmsCode(Object principal, Object credentials,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    /**
     * 获取登录凭证
     */
    @Override
    public Object getCredentials() {
        return credentials;
    }

    /**
     * 获取认证主体
     */
    @Override
    public Object getPrincipal() {
        return  principal ;
    }
}

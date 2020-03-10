package com.micro.bbqentry.security.model;


import org.springframework.security.core.GrantedAuthority;

/**
 * 用户角色类型
 *
 * @author jockeys
 * @since 2020/2/6
 */
public class MyUserRole implements GrantedAuthority {

    private String authority;

    public MyUserRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
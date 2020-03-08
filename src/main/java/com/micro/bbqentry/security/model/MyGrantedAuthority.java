package com.micro.bbqentry.security.model;



import org.springframework.security.core.GrantedAuthority;

/**
 * 权限类型，负责存储权限和角色
 *
 * @author jockeys
 * @since 2020/2/6
 */
public class MyGrantedAuthority implements GrantedAuthority {

    private String authority;

    public MyGrantedAuthority(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
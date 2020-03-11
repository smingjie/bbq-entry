package com.micro.bbqentry.security.model;

import com.google.common.collect.Maps;
import com.micro.bbqentry.model.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * {MyUser} class 自定义需要存储的当前登录用户必要信息
 *
 * @author jockeys
 * @since 2020/2/5
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements UserDetails {

    private String userId;
    private String username;
    private String phone;
    private boolean enabled;

    /**
     * 转换为map形式存储
     */
    public Map<String, String> asMap() {
        log.info("调用asMap()方法，转换为Map形式");
        Map<String, String> map = Maps.newHashMap();
        map.put("userId", this.userId);
        map.put("username", this.username);
        map.put("phone", this.phone);
        return map;
    }

    /**
     * 从map中解析指定内容
     */
    public static MyUser phaseByMap(Map<String, String> map) {
        log.info("调用phaseByMap()方法，装载为框架需要的MyUser类");
        MyUser myUser = new MyUser();
        myUser.setUserId(map.get("userId"));
        myUser.setUsername(map.get("username"));
        myUser.setPhone(map.get("phone"));
        return myUser;
    }

    /**
     * 根据数据库查出的实体 Entity 解析为框架需要的 MyUser类
     */

    public static MyUser phaseByEntity(SysUserEntity entity) {
        log.info("调用phaseByEntity()方法，装载为框架需要的MyUser类");
        MyUser myUser = new MyUser();
        myUser.setUserId(entity.getUserId());
        myUser.setUsername(entity.getUsername());
        myUser.setPhone(entity.getMobile());
        return myUser;
    }

    /**
     * 权限集合 暂时忽略掉
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    /**
     * 帐户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * 帐户密码是否过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 帐号是否可用
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

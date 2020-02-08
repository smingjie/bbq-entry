package com.micro.bbqentry.security.under;


import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysUser;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 自定义身份认证验证组件
 *
 * @author jockeys
 * @since 2020/2/6
 */
public class MyCustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * 用户相关的服务接口
     */
    private MyCustomUserService userService;

    /**
     * 加密方式
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 构造器 注入
     */
    public MyCustomAuthenticationProvider(MyCustomUserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户标识 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 认证逻辑，注意先后顺序
        // 先查询出来用户详情
        SysUser sysUser = userService.loadUserByUniqueKey(name);
        // 密码匹配校验
        if (!bCryptPasswordEncoder.matches(password, sysUser.getPassword())) {
            throw new BadCredentialsException("登录名或密码错误");
        }
        // 账号是否禁用
        if (sysUser.getStatus() == OpenConstant.ENABLED_FALSE) {
            throw new DisabledException("账号被禁用");
        }
        // 校验通过后,重组用户信息为MyUser
        MyUser myUser = MyUser.phaseByEntity(sysUser);
        // 把MyUser信息存储到principal, 当然你也可以放其他内容
        Authentication auth = new UsernamePasswordAuthenticationToken(myUser, password, null);
        return auth;
    }


    /**
     * 是否可以提供输入类型的认证服务
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
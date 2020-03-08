package com.micro.bbqentry.security.provider;


import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysUserEntity;
import com.micro.bbqentry.security.service.UserService;
import com.micro.bbqentry.security.model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 身份认证验证组件(账号密码方式)，可拆解为2个任务：
 * 1获取信息：通常是数据层的操作，从其他数据源（如mysql/redis/ldap等）获取待认证的数据，
 * 2验证信息：表现为验证算法选取，外部数据与获取的数据做校验，需要加密规则 PasswordEncoder
 *
 * @author jockeys
 * @since 2020/2/6
 */
@Slf4j
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    /**
     * 构造器 注入
     */
    public UsernamePasswordAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
        try {
            String name = authentication.getName();
            String password = authentication.getCredentials().toString();
            // 1 认证逻辑任务1，先获取信息
            SysUserEntity sysUser = userService.loadUserByUniqueKey(name);
            // 2 认证逻辑任务2，通过注入的{PasswordEncoder}匹配校验
            if (!passwordEncoder.matches(password, sysUser.getPassword())) {
                throw new BadCredentialsException("登录名或密码错误");
            }
            // 3 额外校验，账号是否禁用
            if (sysUser.getStatus() == OpenConstant.ENABLED_FALSE) {
                throw new DisabledException("账号被禁用");
            }
            // 4 校验通过后重组用户信息为MyUser
            MyUser myUser = MyUser.phaseByEntity(sysUser);
            // 把MyUser信息存储到principal, 当然你也可以放其他内容
            return new UsernamePasswordAuthenticationToken(myUser, password, null);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new BadCredentialsException("认证未知异常");
        }
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
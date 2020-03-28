package com.micro.bbqentry.security.service;

import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysUser;
import com.micro.bbqentry.security.model.AuthenticationTokenJwt;
import com.micro.bbqentry.security.model.AuthenticationTokenSmsCode;
import com.micro.bbqentry.security.model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author jockeys
 * @since 2020/3/11
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 手机短信登录
     *
     * @param authentication AuthenticationTokenSmsCode
     * @return AuthenticationTokenJwt
     */
    public AuthenticationTokenJwt loginBySmsCode(AuthenticationTokenSmsCode authentication) {
        try {
            String phone = authentication.getPrincipal().toString();
            String code = authentication.getCredentials().toString();
            // 1 认证逻辑，查验验证码信息
            if (!smsCodeService.isMatches(phone, code)) {
                throw new BadCredentialsException("短信验证码验证失败");
            }
            // 2 认证逻辑，查验用户信息（根据手机号查询）
            SysUser sysUser = userService.loadUserByUniqueKey(phone);
            // 3 额外校验，账号是否禁用
            if (sysUser.getStatus() == OpenConstant.ENABLED_FALSE) {
                throw new DisabledException("账号被禁用");
            }
            // 把MyUser信息存储到principal, 当然你也可以放其他内容
            return new AuthenticationTokenJwt(MyUser.phaseByEntity(sysUser), null, null);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new BadCredentialsException("认证未知异常");
        }
    }

    /**
     * 账号密码登录
     *
     * @param authentication UsernamePasswordAuthenticationToken
     * @return AuthenticationTokenJwt
     */
    public AuthenticationTokenJwt loginByUsernamePassword(UsernamePasswordAuthenticationToken authentication) {
        try {
            String name = authentication.getName();
            String password = authentication.getCredentials().toString();
            // 1 认证逻辑任务1，先获取信息
            SysUser sysUser = userService.loadUserByUniqueKey(name);
            // 2 认证逻辑任务2，通过注入的{PasswordEncoder}匹配校验
            if (!passwordEncoder.matches(password, sysUser.getPassword())) {
                throw new BadCredentialsException("登录名或密码错误");
            }
            // 3 额外校验，账号是否禁用
            if (sysUser.getStatus() == OpenConstant.ENABLED_FALSE) {
                throw new DisabledException("账号被禁用");
            }
            // 把MyUser信息存储到principal, 当然你也可以放其他内容
            return new AuthenticationTokenJwt(MyUser.phaseByEntity(sysUser), null, null);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new BadCredentialsException("认证未知异常");
        }
    }
}

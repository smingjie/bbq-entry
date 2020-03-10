package com.micro.bbqentry.security.provider;

import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysUserEntity;
import com.micro.bbqentry.security.model.MyUser;
import com.micro.bbqentry.security.model.PhoneCodeAuthenticationToken;
import com.micro.bbqentry.security.service.PhoneCodeService;
import com.micro.bbqentry.security.service.UserService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 身份认证验证组件(手机短信方式)，可拆解为2个任务：获取用户信息，验证短信信息，
 * 相比于账号密码方式 省去了PasswordEncoder，但多了查验短信验证码过程
 *
 * @author jockeys
 * @since 2020/3/8
 */
public class PhoneCodeAuthenticationProvider implements AuthenticationProvider {

    private PhoneCodeService phoneCodeService;
    private UserService userService;

    public PhoneCodeAuthenticationProvider(UserService userService, PhoneCodeService phoneCodeService) {
        this.phoneCodeService = phoneCodeService;
        this.userService = userService;
    }

    /**
     * 真实的验证过程，针对于该AuthenticationProvider特别重要
     * {@link AuthenticationManager#authenticate(Authentication)}
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String phone = authentication.getPrincipal().toString();
            String code = authentication.getCredentials().toString();
            // 1 认证逻辑，查验验证码信息
            if (!phoneCodeService.isMatches(phone, code)) {
                throw new BadCredentialsException("短信验证码验证失败");
            }
            // 2 认证逻辑，查验用户信息（根据手机号查询）
            SysUserEntity sysUser = userService.loadUserByUniqueKey(phone);
            // 3 额外校验，账号是否禁用
            if (sysUser.getStatus() == OpenConstant.ENABLED_FALSE) {
                throw new DisabledException("账号被禁用");
            }
            // 把MyUser信息存储到principal, 当然你也可以放其他内容
            return new PhoneCodeAuthenticationToken(MyUser.phaseByEntity(sysUser), null, null);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new BadCredentialsException("认证未知异常");
        }
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(PhoneCodeAuthenticationToken.class);

    }
}

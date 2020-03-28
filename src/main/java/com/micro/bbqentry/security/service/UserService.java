package com.micro.bbqentry.security.service;

import com.micro.bbqentry.general.utils.ValidatorUtils;
import com.micro.bbqentry.model.entity.SysUser;
import com.micro.bbqentry.repository.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 为了获取用户信息，实现security框架的 UserDetailsService
 *
 * @author jockeys
 * @since 2020/2/6
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private SysUserMapper userMapper;

    /**
     * 根据用户标识查询用户
     *
     * @param uk 可以为邮箱、手机号、账号等
     * @return
     * @throws UsernameNotFoundException
     */

    public SysUser loadUserByUniqueKey(String uk) throws AuthenticationException {
        SysUser sysUser = null;
        if (ValidatorUtils.isEmail(uk)) {
            log.info("用户标识{}为邮箱", uk);
            sysUser = userMapper.selectByEmail(uk);
        } else if (ValidatorUtils.isMobile(uk)) {
            log.info("用户标识{}为手机号", uk);
            sysUser = userMapper.selectByMobile(uk);
        } else { //根据用户账号，从数据库取出用户信息
            log.info("用户标识{}为账号", uk);
            sysUser = userMapper.selectByUsername(uk);
        }
        //若为空 抛出异常
        if (sysUser == null) {
            log.info("用户{}不存在", uk);
            throw new UsernameNotFoundException("user({" + uk + "}) do not exist!");
        }
        // TODO 根据用户账号，查找到权限
        return sysUser;
    }


}
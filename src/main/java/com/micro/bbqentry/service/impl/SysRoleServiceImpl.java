package com.micro.bbqentry.service.impl;

import com.google.common.collect.Lists;
import com.micro.bbqentry.model.entity.SysRole;
import com.micro.bbqentry.model.param.SysRoleDTO;
import com.micro.bbqentry.repository.SysRoleMapper;
import com.micro.bbqentry.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jockeys
 * @since 2020/3/1
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据用户id获取用户的角色
     *
     * @param userId 用户id
     * @return 用户角色集合
     */
    @Override
    public List<SysRoleDTO> getRolesByUserId(String userId) {
        List<SysRole> entities = sysRoleMapper.selectByUserId(userId);
        return Lists.transform(entities,SysRoleDTO::new);
    }
}

package com.micro.bbqentry.service.impl;

import com.google.common.collect.Lists;
import com.micro.bbqentry.model.entity.SysRoleEntity;
import com.micro.bbqentry.model.param.SysRoleDTO;
import com.micro.bbqentry.repository.SysRoleMapper;
import com.micro.bbqentry.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author jockeys
 * @since 2020/3/1
 */
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据用户id获取用户的角色
     *
     * @param userId 用户id
     * @return 用户角色集合
     */
    @Override
    public List<SysRoleDTO> queryRolesByUserId(String userId) {
        List<SysRoleEntity> entities = sysRoleMapper.queryRolesByUserId(userId);
        List<SysRoleDTO> result=Lists.transform(entities,SysRoleDTO::new);
        return result;
    }
}

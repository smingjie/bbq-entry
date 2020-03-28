package com.micro.bbqentry.service;

import com.micro.bbqentry.model.param.SysRoleDTO;

import java.util.List;

/**
 * @author jockeys
 * @since 2020/3/1
 */
public interface ISysRoleService {
    /**
     * 根据用户id获取用户的角色
     *
     * @param userId 用户id
     * @return 用户角色集合
     */
    List<SysRoleDTO> getRolesByUserId(String userId);
}

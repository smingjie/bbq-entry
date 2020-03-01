package com.micro.bbqentry.model.entity;

import lombok.Data;

/**
 * 用户与角色关系表(SysUserRole)实体类
 *
 * @author makejava
 * @since 2020-03-01 16:16:07
 */
@Data
public class SysUserRoleEntity {
     
    /**
    * 唯一id
    */
    private String id;
    /**
    * 角色id
    */
    private String roleId;
    /**
    * 用户id
    */
    private String userId;
 
}
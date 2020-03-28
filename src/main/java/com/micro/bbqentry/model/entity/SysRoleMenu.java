package com.micro.bbqentry.model.entity;

import lombok.Data;

/**
 * 角色与菜单对应关系(SysRoleMenu)实体类
 *
 * @author makejava
 * @since 2020-03-01 16:16:07
 */
@Data
public class SysRoleMenu {
     
    /**
    * 唯一id
    */
    private String id;
    /**
    * 角色ID
    */
    private String roleId;
    /**
    * 菜单ID
    */
    private String menuId;
 
}
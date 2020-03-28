package com.micro.bbqentry.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 菜单管理(SysMenu)实体类
 *
 * @author makejava
 * @since 2020-03-01 16:16:07
 */
@Data
public class SysMenu {
     
    /**
    * 唯一Id
    */
    @TableId
    private String menuId;
    /**
    * 父菜单ID，一级菜单为0
    */
    private String parentId;
    /**
    * 菜单名称
    */
    private String name;
    /**
    * 菜单URL
    */
    private String url;
    /**
    * 类型   0：目录   1：菜单   2：按钮
    */
    private Integer type;
    /**
    * 菜单图标
    */
    private String icon;
    /**
    * 排序
    */
    private Integer orderNum;
 
}
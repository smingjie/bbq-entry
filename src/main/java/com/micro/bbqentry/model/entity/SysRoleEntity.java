package com.micro.bbqentry.model.entity;

import java.util.Date;
import lombok.Data;

/**
 * 用户角色(SysRole)实体类
 *
 * @author makejava
 * @since 2020-03-01 16:14:19
 */
@Data
public class SysRoleEntity {
     
    /**
    * 唯一id
    */
    private String roleId;
    /**
    * 角色代码
    */
    private String roleCode;
    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 角色类型 U自定义 S系统预设
    */
    private String roleType;
    /**
    * 备注
    */
    private String remark;
    /**
    * 状态  0：禁用  1：正常
    */
    private Object status;
    /**
    * 创建人
    */
    private String createBy;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新人
    */
    private String updateBy;
    /**
    * 最后更新时间
    */
    private Date updateTime;
 
}
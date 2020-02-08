package com.micro.bbqentry.model.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * 系统用户(SysUser)实体类
 *
 * @author makejava
 * @since 2020-02-03 18:45:07
 */
@Data
public class SysUser {
     
    /**
    * 唯一id
    */
    private String userId;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 手机号
    */
    private String mobile;
    /**
    * 状态  0：禁用  1：正常
    */
    private Integer status;
    /**
    * 创建人
    */
    private String createdBy;
    /**
    * 创建时间
    */
    private Date createdTime;
    /**
    * 更新人
    */
    private String updatedBy;
    /**
    * 最后更新时间
    */
    private Date updatedTime;
 
}
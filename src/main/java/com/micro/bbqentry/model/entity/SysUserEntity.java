package com.micro.bbqentry.model.entity;

import java.time.LocalDateTime;

import lombok.Data;


/**
 * 系统用户(SysUser)实体类
 *
 * @author makejava
 * @since 2020-02-03 18:45:07
 */
@Data
public class SysUserEntity {

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
     * 状态  -1:已删除 0：禁用  1：正常
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

}
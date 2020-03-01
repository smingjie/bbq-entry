package com.micro.bbqentry.model.entity;

import lombok.Data;

/**
 * 系统配置信息表(SysConfig)实体类
 *
 * @author makejava
 * @since 2020-02-03 18:45:07
 */
@Data
public class SysConfigEntity {
     
    /**
    * 唯一ID
    */
    private String id;
    /**
    * key
    */
    private String paramKey;
    /**
    * value
    */
    private String paramValue;
    /**
    * 状态   0：隐藏   1：显示
    */
    private Integer status;
    /**
    * 备注
    */
    private String remark;
 
}
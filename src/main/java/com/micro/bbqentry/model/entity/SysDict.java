package com.micro.bbqentry.model.entity;

import java.util.Date;
import lombok.Data;

/**
 * 数据字典表(SysDict)实体类
 *
 * @author makejava
 * @since 2020-02-03 18:45:07
 */
@Data
public class SysDict {
     
    
    private String id;
    /**
    * 字典名称
    */
    private String name;
    /**
    * 字典类型
    */
    private String type;
    /**
    * 字典码
    */
    private String code;
    /**
    * 字典值
    */
    private String value;
    /**
    * 排序
    */
    private Integer orderNum;
    /**
    * 备注
    */
    private String remark;
    /**
    * 删除标记  -1：已删除  0：正常
    */
    private Integer delFlag;
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
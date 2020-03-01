package com.micro.bbqentry.repository.sqlprovider;

import com.google.common.base.Strings;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysDictEntity;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author jockeys
 * @since 2020/2/4
 */
public class SysDictSqlProvider {
    static final String curTableName = "sys_dict";

    public String insertEntity(SysDictEntity entity) {
        SQL sql = new SQL().INSERT_INTO(curTableName);
        sql.VALUES("id", "#{id}");

        if (!Strings.isNullOrEmpty(entity.getName())) {
            sql.VALUES("name", "#{name}");
        }
        if (!Strings.isNullOrEmpty(entity.getType())) {
            sql.VALUES("type", "#{type}");
        }
        if (!Strings.isNullOrEmpty(entity.getCode())) {
            sql.VALUES("code", "#{code}");
        }
        if (!Strings.isNullOrEmpty(entity.getValue())) {
            sql.VALUES("value", "#{value}");
        }
        if (!Strings.isNullOrEmpty(entity.getRemark())) {
            sql.VALUES("remark", "#{remark}");
        }
        if (null != entity.getOrderNum()) {
            sql.VALUES("order_num", "#{orderNum}");
        }
        if (null != entity.getDelFlag()) {
            sql.VALUES("del_flag", "#{delFlag}");
        }
        sql.VALUES("create_by", "#{createBy}");
        sql.VALUES("create_Time", "#{createTime}");
        return sql.toString();
    }

    public String updateEntity(SysDictEntity entity) {
        SQL sql = new SQL().UPDATE(curTableName);

        if (!Strings.isNullOrEmpty(entity.getName())) {
            sql.SET("name=#{name}");
        }
        if (!Strings.isNullOrEmpty(entity.getType())) {
            sql.SET("type=#{type}");
        }
        if (!Strings.isNullOrEmpty(entity.getCode())) {
            sql.SET("code=#{code}");
        }
        if (!Strings.isNullOrEmpty(entity.getValue())) {
            sql.SET("value=#{value}");
        }
        if (!Strings.isNullOrEmpty(entity.getRemark())) {
            sql.SET("remark=#{remark}");
        }
        if (null != entity.getOrderNum()) {
            sql.SET("order_num=#{orderNum}");
        }
        if (null != entity.getDelFlag()) {
            sql.SET("del_flag=#{delFlag}");
        }
        sql.SET("update_by=#{updateBy}");
        sql.SET("update_Time=#{updateTime}");
        sql.WHERE("id=#{id}");
        return sql.toString();
    }
}

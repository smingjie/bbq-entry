package com.micro.bbqentry.repository.sqlprovider;

import com.google.common.base.Strings;
import com.micro.bbqentry.model.entity.SysUserEntity;
import org.apache.ibatis.jdbc.SQL;


/**
 * @author jockeys
 * @since 2020/2/4
 */
public class SysUserSqlProvider {


    public String insertEntity(SysUserEntity entity) {
        SQL sql = new SQL().INSERT_INTO("sys_user");
        sql.VALUES("user_id", "#{userId}");
        if (!Strings.isNullOrEmpty(entity.getUsername())) {
            sql.VALUES("username", "#{username}");
        }
        if (!Strings.isNullOrEmpty(entity.getPassword())) {
            sql.VALUES("password", "#{password}");
        }
        if (!Strings.isNullOrEmpty(entity.getEmail())) {
            sql.VALUES("email", "#{email}");
        }
        if (!Strings.isNullOrEmpty(entity.getMobile())) {
            sql.VALUES("mobile", "#{mobile}");
        }
        if (null != entity.getStatus()) {
            sql.VALUES("status", "#{status}");
        }
        sql.VALUES("created_by", "#{createdBy}");
        sql.VALUES("created_Time", "#{createdTime}");
        return sql.toString();
    }

    public String updateEntity(SysUserEntity entity) {
        SQL sql = new SQL().UPDATE("sys_user");
        if (!Strings.isNullOrEmpty(entity.getUsername())) {
            sql.SET("username=#{username}");
        }
        if (!Strings.isNullOrEmpty(entity.getPassword())) {
            sql.SET("password=#{password}");
        }
        if (!Strings.isNullOrEmpty(entity.getEmail())) {
            sql.SET("email=#{email}");
        }
        if (!Strings.isNullOrEmpty(entity.getMobile())) {
            sql.SET("mobile=#{mobile}");
        }
        if (null != entity.getStatus()) {
            sql.SET("status=#{status}");
        }
        sql.SET("updated_by=#{updatedBy}");
        sql.SET("updated_Time=#{updatedTime}");
        sql.WHERE("id=#{id}");
        return sql.toString();
    }
}

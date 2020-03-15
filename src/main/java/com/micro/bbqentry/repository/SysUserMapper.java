package com.micro.bbqentry.repository;


import com.micro.bbqentry.model.entity.SysUserEntity;
import com.micro.bbqentry.repository.sqlprovider.SysUserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * 系统用户(SysDictEntity)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 22:34:24
 */
public interface SysUserMapper  {

    /**
     * 通过主键id查询单条数据
     */
    @Select("select * from sys_user where user_id=#{userId} ")
    SysUserEntity queryByUserId(@Param("userId") String userId);

    /**
     * 通过账号username查询单条数据
     */
    @Select("select * from sys_user where username=#{username} ")
    SysUserEntity queryByUsername(@Param("username") String username);

    /**
     * 通过手机号查询单条数据
     */
    @Select("select * from sys_user where mobile=#{mobile} ")
    SysUserEntity queryByMobile(@Param("mobile") String mobile);

    /**
     * 通过邮箱查询单条数据
     */
    @Select("select * from sys_user where email=#{email} ")
    SysUserEntity queryByEmail(@Param("email") String email);

    /**
     * 新增数据
     */
    @InsertProvider(type = SysUserSqlProvider.class, method = "insertEntity")
    int insert(SysUserEntity entity);

    /**
     * 修改数据
     */
    @UpdateProvider(type = SysUserSqlProvider.class, method = "updateEntity")
    int update(SysUserEntity entity);

    /**
     * 禁用账号，通过更新状态标记位为 0
     */
    @Update("update sys_user " +
            " set status=-1,update_by=#{updateBy},update_time=#{updateTime} " +
            " where user_id=#{userId}")
    int updateStatusAsForbidden(@Param("userId") String userId,
                                @Param("updateBy") String updateBy,
                                @Param("updateTime") Date updateTime);

    /**
     * 删除数据：通过主键id
     */
    @Delete("delete * from sys_user where id=#{id}")
    int deleteById(@Param("id") String id);

}
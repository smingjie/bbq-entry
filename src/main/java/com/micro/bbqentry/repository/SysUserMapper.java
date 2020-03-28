package com.micro.bbqentry.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.micro.bbqentry.model.entity.SysUser;
import org.apache.ibatis.annotations.*;


/**
 * 系统用户(SysDict)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 22:34:24
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 通过账号username查询单条数据
     */
    @Select("select * from sys_user where username=#{username} ")
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 通过手机号查询单条数据
     */
    @Select("select * from sys_user where mobile=#{mobile} ")
    SysUser selectByMobile(@Param("mobile") String mobile);

    /**
     * 通过邮箱查询单条数据
     */
    @Select("select * from sys_user where email=#{email} ")
    SysUser selectByEmail(@Param("email") String email);

}
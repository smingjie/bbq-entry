package com.micro.bbqentry.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.micro.bbqentry.model.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色Mapper
 *
 * @author jockeys
 * @since 2020/3/1
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("select sr.* from sys_role sr where sr.role_id in (" +
            " select sur.role_id from sys_user_role sur where sur.user_id=#{userId})")
    List<SysRole> selectByUserId(@Param("userId") String userId);


}

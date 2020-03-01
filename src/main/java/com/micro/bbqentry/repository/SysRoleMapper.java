package com.micro.bbqentry.repository;

import com.micro.bbqentry.model.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色Mapper
 *
 * @author jockeys
 * @since 2020/3/1
 */
@Mapper
public interface SysRoleMapper {
    @Select("select sr.* from sys_role sr where sr.role_id in (" +
            " select sur.role_id from sys_user_role sur where sur.user_id=#{userId})")
    List<SysRoleEntity> queryRolesByUserId(@Param("userId") String userId);


}

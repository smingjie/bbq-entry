package com.micro.bbqentry.repository;

import com.micro.bbqentry.model.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashSet;
import java.util.List;

/**
 * 系统菜单Mapper
 *
 * @author jockeys
 * @since 2020/3/1
 */
public interface SysMenuMapper {
    /**
     * 根据菜单ids查询菜单集合
     *
     * @param menuIds 菜单ids
     * @return 菜单集合
     */
    @Select("<script>select sm.* from sys_menu sm where sm.menu_id in " +
            "<foreach collection='menuIds' open='(' item='menuId' separator=',' close=')'>#{menuId}</foreach>" +
            "</script>")
    List<SysMenuEntity> queryMenusByMenuIds(@Param("menuIds") HashSet<String> menuIds);

    /**
     * 根据角色ids查询菜单集合
     *
     * @param roleIds 角色ids
     * @return 菜单集合
     */
    @Select("<script>select sm.* from sys_menu sm " +
            " inner join sys_role_menu srm on sm.menu_id=srm.menu_id" +
            " where srm.role_id in " +
            "<foreach collection='roleIds' open='(' item='roleId' separator=',' close=')'>#{roleId}</foreach>" +
            "</script>")
    List<SysMenuEntity> queryMenusByRoleIds(@Param("roleIds") HashSet<String> roleIds);

    /**
     * 根据用户id询菜单集合
     *
     * @param userId 用户id
     * @return 菜单集合
     */
    @Select("select sm.* from sys_menu sm " +
            " inner join sys_role_menu srm on sm.menu_id=srm.menu_id" +
            " where srm.role_id in( " +
            " select sur.role_id from sys_user_role sur where sur.user_id=#{userId})")
    List<SysMenuEntity> queryMenusByUserId(@Param("userId") String userId);
}

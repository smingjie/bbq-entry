package com.micro.bbqentry.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.micro.bbqentry.model.entity.SysMenu;
import com.micro.bbqentry.model.param.SysMenuDTO;
import com.micro.bbqentry.repository.SysMenuMapper;
import com.micro.bbqentry.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 菜单管理服务实现类
 *
 * @author jockeys
 * @since 2020/3/1
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据用户id获取菜单树 JSONArray对象
     *
     * @param userId 用户id
     * @return JSONArray对象
     */
    @Override
    public JSONArray getMenuTreeByUserId(String userId) {
        List<SysMenu> allMenus = sysMenuMapper.selectMenusByUserId(userId);
        return createMenuTree(allMenus);
    }

    /**
     * 获取子菜单
     * todo test method
     *
     * @param menuList 所有菜单项
     * @param parentId 父菜单id
     * @return 菜单集合(json)
     */
    private JSONArray getSubMenus(List<SysMenu> menuList, String parentId) {
        JSONArray childMenuList = new JSONArray();
        menuList.forEach(o -> {
            if (parentId.equalsIgnoreCase(o.getParentId())) {
                childMenuList.add(new SysMenuDTO(o).toJSONObject()
                        .fluentPut("children", getSubMenus(menuList, o.getMenuId())));
            }
        });
        return childMenuList.isEmpty() ? null : childMenuList;
    }

    /**
     * 菜单树生成
     *
     * @param menuList 所有菜单项
     * @return JSONArray
     */
    private JSONArray createMenuTree(List<SysMenu> menuList) {
        JSONArray menuTreeArray = new JSONArray();
        // 先筛选出父元素
        List<SysMenu> fatMenus = menuList.stream()
                .filter(o -> o.getType().equals(0))
                .collect(Collectors.toList());
        // 遍历集合
        fatMenus.forEach(o -> menuTreeArray.add(new SysMenuDTO(o)
                .toJSONObject().fluentPut("children", getSubMenus(menuList, o.getMenuId()))));

        return menuTreeArray.isEmpty() ? null : menuTreeArray;
    }

}

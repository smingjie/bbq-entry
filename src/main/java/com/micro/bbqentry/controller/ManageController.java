package com.micro.bbqentry.controller;

import com.alibaba.fastjson.JSONArray;
import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.model.param.SysRoleDTO;
import com.micro.bbqentry.service.ISysMenuService;
import com.micro.bbqentry.service.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 个人中心-控制器
 *
 * @author jockeys
 * @since 2020/3/1
 */
@RestController
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysMenuService iSysMenuService;

    @ApiOperation(value = "获取用户的角色列表")
    @GetMapping("/users/{userId}/roles")
    public ResponseJson getUserRoles(@PathVariable String userId) {
        List<SysRoleDTO> list = iSysRoleService.queryRolesByUserId(userId);
        return ResponseJson.ok(list);
    }

    @ApiOperation(value = "获取用户的菜单（树）列表")
    @GetMapping("/users/{userId}/menus")
    public ResponseJson getUserMenus(@PathVariable String userId) {
        JSONArray data = iSysMenuService.getMenuTreeByUserId(userId);
        return ResponseJson.ok(data);
    }


}

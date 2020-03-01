package com.micro.bbqentry.controller;

import com.micro.bbqentry.general.common.ResponseJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心-控制器
 *
 * @author jockeys
 * @since 2020/3/1
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

    @ApiOperation(value = "获取用户的角色列表")
    @GetMapping("/users/{userId}/roles")
    public ResponseJson getUserRoles(@PathVariable String userId) {

        return ResponseJson.ok();
    }
}

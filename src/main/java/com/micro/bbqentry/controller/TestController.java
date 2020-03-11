package com.micro.bbqentry.controller;

import com.micro.bbqentry.general.annotation.ResponseFormat;
import com.micro.bbqentry.model.param.SysRoleDTO;
import com.micro.bbqentry.service.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jockeys
 * @since 2020/3/3
 */
@Slf4j
@RestController
@ResponseFormat
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ISysRoleService iSysRoleService;

    @ApiOperation(value = "获取用户的菜单（树）列表")
    @GetMapping("/methods")
    public List<SysRoleDTO> testMethod() {
        String userId = "DFCC2080-8CAB-48A7-9FA9-0000A90DDFA5";
        List<SysRoleDTO> data = iSysRoleService.queryRolesByUserId(userId);
        return data;
    }
}

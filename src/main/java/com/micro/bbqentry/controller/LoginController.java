package com.micro.bbqentry.controller;

import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.model.param.PasswordLoginParam;
import com.micro.bbqentry.model.param.UserDTO;
import com.micro.bbqentry.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录功能-控制器
 *
 * @author jockeys
 * @since 2020/2/3
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    ISysUserService iSysUserService;

    @PostMapping("/logins")
    public ResponseJson login(@RequestBody PasswordLoginParam param) {

        UserDTO dto=iSysUserService.queryByUsername(param.getUsername());

        return ResponseJson.ok(dto);
    }

    @PostMapping("/logout")
    public ResponseJson logout() {

        return ResponseJson.ok().setMsg("登出成功");
    }
}

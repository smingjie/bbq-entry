package com.micro.bbqentry.controller;

import com.google.common.base.Strings;
import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.general.utils.ValidatorUtils;
import com.micro.bbqentry.model.param.UserDTO;
import com.micro.bbqentry.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户-控制器
 *
 * @author jockeys
 * @since 2020/2/6
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    ISysUserService iSysUserService;

    @GetMapping("/{id}")
    public ResponseJson getOneUserById(@PathVariable String id) {

        UserDTO dto = iSysUserService.getById(id);
        return ResponseJson.ok(dto);
    }

    @GetMapping
    public ResponseJson getOneUserByUsername(String username) {

        UserDTO dto = iSysUserService.getByUsername(username);
        return ResponseJson.ok(dto);
    }

    @PostMapping("/")
    public ResponseJson addOneUser(@RequestBody UserDTO param) {
        //当手机号不为空时候检验是否符合手机号规范
        if (Strings.isNullOrEmpty(param.getMobile()) && ValidatorUtils.isMobile(param.getMobile())) {
            return ResponseJson.error(ResponseEnum.ILLEGAL_ARGS_ERROR.getCode(), "手机号码格式不正确");
        }
        if (Strings.isNullOrEmpty(param.getEmail()) && ValidatorUtils.isEmail(param.getEmail())) {
            return ResponseJson.error(ResponseEnum.ILLEGAL_ARGS_ERROR.getCode(), "邮箱号码格式不正确");
        }
        if (Strings.isNullOrEmpty(param.getUsername()) && ValidatorUtils.isUsername(param.getMobile())) {
            return ResponseJson.error(ResponseEnum.ILLEGAL_ARGS_ERROR.getCode(), "注册用户名格式不正确");
        }
        if (iSysUserService.save(param)) {
            return ResponseJson.ok().setMsg("添加新用户成功");
        }
        return ResponseJson.error().setMsg("添加新用户失败");
    }
}

package com.micro.bbqentry.controller;

import com.micro.bbqentry.general.annotation.ResponseFormat;
import com.micro.bbqentry.model.entity.SysUser;
import com.micro.bbqentry.repository.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jockeys
 * @since 2020/3/3
 */
@Slf4j
@RestController
@ResponseFormat
@RequestMapping("/test")
@RequiredArgsConstructor(onConstructor =@_(@Autowired))
public class TestController {

    private final SysUserMapper mapper;
    @GetMapping("/users")
    public SysUser testMethod() {

        return mapper.selectById("7C6B7434-66AD-49E2-9BE4-1619F908ACF7");
    }
}

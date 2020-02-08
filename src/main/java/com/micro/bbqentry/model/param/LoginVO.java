package com.micro.bbqentry.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录功能 传入参数
 *
 * @author jockeys
 * @since 2020/2/3
 */
@Data
public class LoginVO {

    @NotNull(message = "登录账户不能为空")
    private String username;

    @NotNull(message = "登录密码不能为空")
    private String password;

    private String captcha;
}

package com.micro.bbqentry.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录功能 传入参数
 *
 * @author jockeys
 * @since 2020/2/3
 */
@ApiModel
@Data
public class LoginParam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "登录账户不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "登录密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码id")
    private String captchaId;
}

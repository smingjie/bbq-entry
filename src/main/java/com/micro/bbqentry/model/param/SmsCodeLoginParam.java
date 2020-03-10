package com.micro.bbqentry.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jockeys
 * @since 2020/3/10
 */
@ApiModel
@Data
public class SmsCodeLoginParam {

    @ApiModelProperty(value = "手机号", required = true)
    @NotNull(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "短信验证码", required = true)
    @NotNull(message = "短信验证码不能为空")
    private String code;

}

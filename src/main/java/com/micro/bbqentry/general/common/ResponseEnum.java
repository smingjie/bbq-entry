package com.micro.bbqentry.general.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码封装，用枚举类实现
 *
 * @author jockeys
 * @since 2020/2/2
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    /**
     * 成功状态码
     */
    SUCCESS("0", "请求操作成功"),

    /**
     * 通用的校验错误
     */
    ILLEGAL_ARGS_ERROR("args-illegal-err","非法参数错误"),
    ARGS_PASSWORD_WRONG("password-format-err","密码格式不正确"),
    ARGS_EMAIL_WRONG("email-format-err","邮箱格式不正确"),
    ARGS_PHONE_WRONG("phone-format-err","手机格式不正确"),
    /**
     * 系统异常相关错误
     */
    SERVER_ERROR("-1", "未知异常，请联系管理员"),
    SYSTEM_ERROR("system-err", "系统内部错误，请联系管理员..."),
    NETWORK_ERROR("network-err", "网络故障，请检查网络连接..."),
    BUSINESS_ERROR("business-err", "业务处理中，请稍后重试..."),

    /**
     * 用户模块相关错误
     */
    USER_FORBIDDEN("user-forbidden", "该用户被禁用"),
    USER_WRONG("user-wrong", "账号或密码不正确"),
    USER_NOT_EXIST("user-notExist", "该用户不存在"),
    USER_AUTH_FAIL("auth-fail","登录认证失败"),
    /**
     *Token 相关错误
     */
    TOKEN_CREATE_ERROR("token-create-err","token生成失败"),
    TOKEN_PHASE_ERROR("token-phase-err","token解析错误"),
    TOKEN_EXPIRED("token-expired","token已过期"),
    TOKEN_SIGN_ERROR("token-sign-err","token签名错误"),
    ;

    private String code;
    private String message;
}

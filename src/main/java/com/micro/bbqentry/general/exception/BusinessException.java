package com.micro.bbqentry.general.exception;

import com.micro.bbqentry.general.common.ResponseEnum;

/**
 * 自定义异常的基类，继承自运行时异常
 *
 * @author jockeys
 * @since 2020/2/2
 */
public class BusinessException extends RuntimeException {

    protected ResponseEnum responseEnum;

    /**
     * 唯一构造器
     *
     * @param responseEnum 响应码的枚举实例
     */
    public BusinessException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    public String getCode() {
        return this.responseEnum.getCode();
    }

    @Override
    public String getMessage() {
        return this.responseEnum.getMessage();
    }
}

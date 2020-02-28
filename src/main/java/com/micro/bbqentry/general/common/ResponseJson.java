package com.micro.bbqentry.general.common;

import java.util.HashMap;

/**
 * 返回体封装，以json格式返回，包括 code msg data等信息
 *
 * @author jockeys
 * @since 2020/2/2
 */
public final class ResponseJson extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 默认错误码
     */
    private static final String DEFAULT_ERROR_CODE = "-1";

    /**
     * 默认构造函数私有化
     */
    private ResponseJson() {
        put("code", ResponseEnum.SUCCESS.getCode());
        put("msg", ResponseEnum.SUCCESS.getMessage());
    }

    /**
     * 重新设置提示信息
     *
     * @param msg 提示信息
     * @return this object
     */
    public ResponseJson setMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    /**
     * 错误-消息返回体设置
     *
     * @param code 错误状态码
     * @param msg  错误提示信息
     * @return this object
     */
    public static ResponseJson error(String code, String msg) {
        ResponseJson ans = new ResponseJson();
        ans.put("code", code);
        ans.put("msg", msg);
        return ans;
    }

    /**
     * 错误-消息返回体设置（默认错误状态码为 -1）
     *
     * @param msg 错误提示信息
     * @return this object
     */
    public static ResponseJson error(String msg) {
        return error(DEFAULT_ERROR_CODE, msg);
    }

    /**
     * 错误-信息返回体设置，默认错误码和错误提示
     *
     * @return this object
     */
    public static ResponseJson error() {
        return error(ResponseEnum.SERVER_ERROR.getCode(), ResponseEnum.SERVER_ERROR.getMessage());
    }

    /**
     * 成功-信息返回体设置
     *
     * @param data 响应数据
     * @return the object with response data
     */

    public static ResponseJson ok(Object data) {
        ResponseJson ans = new ResponseJson();
        ans.put("data", data);
        return ans;
    }

    /**
     * 成功-信息返回体设置 （无响应数据）
     *
     * @return this object without response data
     */
    public static ResponseJson ok() {
        return new ResponseJson();
    }

}
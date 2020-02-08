package com.micro.bbqentry.general.common;

import java.util.HashMap;
/**
 * 返回体封装，以json格式返回，包括 code msg data等信息
 *
 * @author jockeys
 * @since 2020/2/2
 */
public class ResponseJson extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private ResponseJson() {
        put("code",ResponseEnum.SUCCESS.getCode());
        put("msg", ResponseEnum.SUCCESS.getMessage());
    }

    public ResponseJson setMsg(String msg){
        this.put("msg", msg);
        return this;
    }

    public static ResponseJson error(String code, String msg) {
        ResponseJson ans = new ResponseJson();
        ans.put("code", code);
        ans.put("msg", msg);
        return ans;
    }

    public static ResponseJson error() {
        return error(ResponseEnum.SERVER_ERROR.getCode(),ResponseEnum.SERVER_ERROR.getMessage());
    }

    public static ResponseJson ok(Object  data) {
        ResponseJson ans = new ResponseJson();
        ans.put("data",data);
        return ans;
    }

    public static ResponseJson ok() {
        return new ResponseJson();
    }

}
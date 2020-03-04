package com.micro.bbqentry.model.param;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author jockeys
 * @since 2020/3/3
 */
public class UserParam {
    /**
     * 登录名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像
     */
    private MultipartFile headImage;
}

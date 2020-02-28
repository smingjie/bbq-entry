package com.micro.bbqentry.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 图形验证码接口
 *
 * @author jockeys
 * @since 2020/2/29
 */
public interface ICaptchaService {
    /**
     * 获取图形验证码
     *
     * @return captchaId captchaImage
     */
    Map<String, Object> getCaptcha();

    /**
     * 获取图形验证码（直接传写到响应体中）
     *
     * @param request  请求体
     * @param response 响应体
     * @throws IOException
     */
    void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 做验证
     *
     * @param captchaId   验证码Id
     * @param captchaText 验证码文本
     * @return 验证通过true 否则false
     */
    boolean doCaptcha(String captchaId, String captchaText);
}

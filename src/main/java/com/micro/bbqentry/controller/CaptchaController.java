package com.micro.bbqentry.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.general.utils.GuidUtility;
import com.micro.bbqentry.general.utils.RedisUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码 -控制器
 *
 * @author jockeys
 * @since 2020/2/22
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    //图像前缀
    static final String IMAGE_PREFIX = "data:image/png;base64,";
    //图像前缀
    static final String REDIS_CAPTCHA_KEY = "captcha";
    @Autowired
    private DefaultKaptcha captchaEngine;
    @Autowired
    private RedisUtility redisRepository;
    @PostMapping("/")
    public ResponseJson getCaptcha() {
        //验证码
        String captchaText = captchaEngine.createText();
        //验证码id
        String captchaId = GuidUtility.getIdKey();
        //验证码图片
        BufferedImage captchaImage = captchaEngine.createImage(captchaText);

        //放到redis
        redisRepository.hadd(REDIS_CAPTCHA_KEY, captchaId, captchaText.toLowerCase());

        //返回到前端
        Map<String, Object> data = new HashMap<>();
        data.put("captchaId", captchaId);
        data.put("captchaImage", imageToBase64String(captchaImage));
        return ResponseJson.ok(data);

    }

    @PostMapping("/does")
    public ResponseJson doCaptcha(@RequestParam String captchaId,@RequestParam String captchaText) {
        //从redis 查询验证码
        String text = (String) redisRepository.hget(REDIS_CAPTCHA_KEY, captchaId);
        //验证码图片
        if (captchaText.toLowerCase().equals(text)) {
            return ResponseJson.ok();
        } else {
            return ResponseJson.error("-1", "验证码验证失败");
        }
    }

    /**
     * 图片转为Base64编码字符串(带前缀）
     *
     * @param image BufferedImage 流
     * @return 转换后的编码字符串
     */
    private String imageToBase64String(BufferedImage image) {
        try {
            ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baoStream);
            String base64Str = Base64.getEncoder().encodeToString(baoStream.toByteArray()).trim();
            return IMAGE_PREFIX + base64Str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.micro.bbqentry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.base.Strings;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.general.exception.BusinessException;
import com.micro.bbqentry.general.utils.RedisUtils;
import com.micro.bbqentry.general.utils.SequenceUtils;
import com.micro.bbqentry.service.ICaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 图形验证码实现类
 *
 * @author jockeys
 * @since 2020/2/29
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements ICaptchaService {
    /**
     * 图像格式
     */
    static final String IMAGE_TYPE = "png";
    /**
     * 存储前缀key
     */
    static final String REDIS_CAPTCHA_KEY = "captcha";
    @Autowired
    private DefaultKaptcha captchaEngine;
    @Autowired
    private RedisUtils redisRepository;


    @Override
    public Map<String, Object> getCaptcha() {
        //创建验证码id，text，image
        Tuple3<String, String, BufferedImage> captchaTuple = createCaptcha();
        //放到redis
        redisRepository.vadd(
                captchaTuple.getT1(),
                captchaTuple.getT2().toLowerCase(),
                OpenConstant.CAPTCHA_EXPIRE_TIME
        );
        //返回到前端
        Map<String, Object> data = new HashMap<>(4);
        data.put("captchaId", captchaTuple.getT1());
        data.put("captchaImage", imageToBase64String(captchaTuple.getT3()));
        return data;
    }


    @Override
    public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建验证码id，text，image
        Tuple3<String, String, BufferedImage> captchaTuple = createCaptcha();
        //放到redis
        redisRepository.vadd(
                captchaTuple.getT1(),
                captchaTuple.getT2().toLowerCase(),
                OpenConstant.CAPTCHA_EXPIRE_TIME
        );
        // 设置响应的类型格式为图片格式
        response.setContentType("image/" + IMAGE_TYPE);
        //禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        JSONObject body = new JSONObject().fluentPut("captchaId", captchaTuple.getT1());
        response.getWriter().write(body.toJSONString());
        OutputStream out = response.getOutputStream();
        try {
            // write the data out
            ImageIO.write(captchaTuple.getT3(), IMAGE_TYPE, out);
            out.flush();
        } finally {
            out.close();
        }
    }

    @Override
    public boolean doCaptcha(String captchaId, String captchaText) {
        //从redis 查询验证码
        String text = redisRepository.vget(captchaId);
        if (Strings.isNullOrEmpty(text)) {
            throw new BusinessException("图形验证码已失效");
        }
        //若有查询结果，说明已用立即清除
        redisRepository.vdel(captchaId);
        //验证码图片
        return (captchaText.equalsIgnoreCase(text));
    }

    /**
     * 图片转为Base64编码字符串(带前缀）
     *
     * @param image BufferedImage 流
     * @return 转换后的编码字符串
     */
    private static String imageToBase64String(BufferedImage image) {
        try {
            ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
            ImageIO.write(image, IMAGE_TYPE, baoStream);
            String base64Str = Base64.getEncoder().encodeToString(baoStream.toByteArray()).trim();
            return MessageFormat.format("data:image/{0};base64,{1}", IMAGE_TYPE, base64Str);
        } catch (IOException e) {
            log.info("图片转为Base64编码字符串过程出错");
            return null;
        }
    }

    /**
     * 创建验证码-组合结构
     *
     * @return captchaId captchaText captchaImage
     */
    private Tuple3<String, String, BufferedImage> createCaptcha() {
        //验证码id
        String captchaId = SequenceUtils.timestampNo(REDIS_CAPTCHA_KEY);
        //验证码
        String captchaText = captchaEngine.createText();
        //验证码图片
        BufferedImage captchaImage = captchaEngine.createImage(captchaText);
        return Tuples.of(captchaId, captchaText, captchaImage);
    }
}

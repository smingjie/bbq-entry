package com.micro.bbqentry.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Google开源验证码 配置类
 *
 * @author jockeys
 * @since 2020/2/22
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDDefaultKaptcha() {
        DefaultKaptcha dk = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "110");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "27");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 背景颜色渐变，开始颜色
        properties.setProperty("kaptcha.background.clear.from", "185,56,213");
        // 背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to", "white");
        // 图片的样式
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        // 干扰实现类
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        // session key
        properties.setProperty("kaptcha.session.key", "code");
        dk.setConfig(new Config(properties));
        return dk;
    }
}

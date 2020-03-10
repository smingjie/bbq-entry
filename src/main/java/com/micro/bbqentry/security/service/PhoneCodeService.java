package com.micro.bbqentry.security.service;


import org.springframework.stereotype.Service;

/**
 * 验证码服务，虚拟的待实现
 *
 * @author jockeys
 * @since 2020/3/8
 */
@Service
public class PhoneCodeService {

    private String phone;
    private String code;
    private boolean used;

    public void getVerifyCodeByPhone(String phone) {
        this.phone = phone;
        this.code = "123456";
        this.used = false;
    }

    public boolean isMatches(String phone, String code) {
        getVerifyCodeByPhone(phone);
        return this.phone.equalsIgnoreCase(phone)
                && this.code.equalsIgnoreCase(code)
                && !this.used;
    }
}

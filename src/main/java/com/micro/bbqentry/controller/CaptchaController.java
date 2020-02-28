package com.micro.bbqentry.controller;


import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.service.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码 -控制器
 *
 * @author jockeys
 * @since 2020/2/22
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @Autowired
    private ICaptchaService iCaptchaService;

    @PostMapping("/")
    public ResponseJson getCaptcha() {
        return ResponseJson.ok(iCaptchaService.getCaptcha());
    }


    @GetMapping("/image")
    public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        iCaptchaService.getCaptchaImage(request, response);
    }

    @PostMapping("/do")
    public ResponseJson doCaptcha(@RequestParam String captchaId, @RequestParam String captchaText) {
        if (iCaptchaService.doCaptcha(captchaId, captchaText)) {
            return ResponseJson.ok();
        }
        return ResponseJson.error("图形验证码验证失败");
    }
}

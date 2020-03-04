package com.micro.bbqentry.controller;

import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.model.param.UserParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jockeys
 * @since 2020/3/3
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping(value = "/info")
    public ResponseJson submitInfoIncludeFile(@RequestPart MultipartFile file,@RequestPart UserParam param){

        log.info("received:{}",param);
        return ResponseJson.ok();
    }
}

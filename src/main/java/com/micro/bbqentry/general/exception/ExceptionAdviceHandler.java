package com.micro.bbqentry.general.exception;

import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.common.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理器
 * java中异常分类 Exception->(IOException & RuntimeException)，详情请自行查阅文档
 *
 * @author jockeys
 * @since 2020/2/2
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdviceHandler {

//    /**
//     * 业务异常捕获并统一处理
//     */
//    @ExceptionHandler(value = BusinessException.class)
//    @ResponseBody
//    public ResponseJson handle(BusinessException e) {
//        log.error("系统业务异常：{}", e.getMessage());
//        return ResponseJson.error(e.getCode(), e.getMessage());
//    }
//    /**
//     * token异常捕获并统一处理
//     */
//    @ExceptionHandler(value = TokenException.class)
//    @ResponseBody
//    public ResponseJson handle(TokenException e) {
//        log.error("token过程异常：{}", e.getMessage());
//        return ResponseJson.error(e.getCode(), e.getMessage());
//    }
    /**
     * RuntimeException异常捕获并统一处理
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseJson handle(RuntimeException e) {

        log.error("RuntimeException异常或其子类异常捕获：{}", e.getMessage());

        if(e instanceof BusinessException){
            BusinessException ex=(BusinessException)e;
            log.error("{}：{}", ex.getClass(),ex.getMessage());
            return ResponseJson.error(ex.getCode(), ex.getMessage());
        }
        return ResponseJson.error(ResponseEnum.SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 顶级异常捕获并统一处理，当其他异常无法处理时候选择使用
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseJson handle(Exception e) {
        log.error("系统内部服务异常：{}", e.getMessage());
        return ResponseJson.error(ResponseEnum.SERVER_ERROR.getCode(), ResponseEnum.SERVER_ERROR.getMessage());
    }
}

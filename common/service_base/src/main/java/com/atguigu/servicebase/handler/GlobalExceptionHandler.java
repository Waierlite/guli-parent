package com.atguigu.servicebase.handler;

import com.atguigu.commonutils.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
@Date: 2022/3/4
@Author: ChenJk
*/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    public ResultJson error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return ResultJson.error().message("执行了全局异常处理...");
    }
}

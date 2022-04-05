package com.atguigu.staservice.client;

import com.atguigu.commonutils.ResultJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
@Date: 2022/3/29
@Author: ChenJk
*/
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    // 统计某一天注册人数
    @GetMapping("/educenter/member/countRegister/{date}")
    public ResultJson countRegister(@PathVariable("date") String date);
}

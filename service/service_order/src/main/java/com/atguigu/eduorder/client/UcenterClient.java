package com.atguigu.eduorder.client;
/*
@Date: 2022/3/25
@Author: ChenJk
*/

import com.atguigu.commonutils.UcenterMemberCommon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    // 根据用户id获取用户信息
    @GetMapping("/educenter/member/getUserById/{id}")
    public UcenterMemberCommon getUserById(@PathVariable("id") String id);
}
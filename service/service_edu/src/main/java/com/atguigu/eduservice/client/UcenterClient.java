package com.atguigu.eduservice.client;

import com.atguigu.commonutils.UcenterMemberCommon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/*
@Date: 2022/3/24
@Author: ChenJk
*/
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientFallbackMethod.class)
public interface UcenterClient {
    // 根据用户id获取用户信息
    @GetMapping("/educenter/member/getUserById/{id}")
    public UcenterMemberCommon getUserById(@PathVariable("id") String id);
}

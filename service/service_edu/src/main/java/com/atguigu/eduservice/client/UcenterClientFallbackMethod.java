package com.atguigu.eduservice.client;
/*
@Date: 2022/3/25
@Author: ChenJk
*/

import com.atguigu.commonutils.UcenterMemberCommon;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientFallbackMethod implements UcenterClient {
    @Override
    public UcenterMemberCommon getUserById(String id) {
        System.out.println("返回用户数据出错，请检查。");
        return null;
    }
}

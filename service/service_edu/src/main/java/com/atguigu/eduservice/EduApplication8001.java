package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/*
@Date: 2022/3/2
@Author: ChenJk
*/
@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
@EnableDiscoveryClient  // 开启Nacos注册
@EnableFeignClients  // 开启OpenFeign
public class EduApplication8001 {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication8001.class,args);
    }
}

package com.atguigu.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/*
@Date: 2022/3/25
@Author: ChenJk
*/
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.eduorder.mapper")
@EnableFeignClients  // 开启OpenFeign
public class OrderApplication8007 {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication8007.class,args);
    }
}

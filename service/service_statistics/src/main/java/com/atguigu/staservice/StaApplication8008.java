package com.atguigu.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
@Date: 2022/3/29
@Author: ChenJk
*/
@SpringBootApplication
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.atguigu.staservice.mapper")
@EnableScheduling
public class StaApplication8008 {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication8008.class,args);
    }
}

package com.atguigu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/*
@Date: 2022/3/20
@Author: ChenJk
*/
@SpringBootApplication
@ComponentScan("com.atguigu")
@MapperScan(basePackages = "com.atguigu.educenter.mapper")

public class UcenterApplication8160 {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication8160.class,args);
    }
}

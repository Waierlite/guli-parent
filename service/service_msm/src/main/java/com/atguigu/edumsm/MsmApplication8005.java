package com.atguigu.edumsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
@Date: 2022/3/19
@Author: ChenJk
*/
@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
public class MsmApplication8005 {

    public static void main(String[] args) {
        SpringApplication.run(MsmApplication8005.class,args);
    }
}

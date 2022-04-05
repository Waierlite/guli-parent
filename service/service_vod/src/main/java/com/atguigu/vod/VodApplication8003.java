package com.atguigu.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/*
@Date: 2022/3/16
@Author: ChenJk
*/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.atguigu")
@EnableDiscoveryClient
public class VodApplication8003 {

    public static void main(String[] args) {
        SpringApplication.run(VodApplication8003.class,args);
    }
}

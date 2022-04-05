package com.atguigu.oss;
/*
@Date: 2022/3/10
@Author: ChenJk
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.atguigu")
@EnableDiscoveryClient
public class OssApplication8002 {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication8002.class,args);
    }
}

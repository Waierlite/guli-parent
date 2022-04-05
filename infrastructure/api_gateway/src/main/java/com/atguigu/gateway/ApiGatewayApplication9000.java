package com.atguigu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
@Date: 2022/4/1
@Author: ChenJk
*/
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication9000 {
    public static void main(String[] args) {
        
        SpringApplication.run(ApiGatewayApplication9000.class,args);
    }
}

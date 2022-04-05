package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/*
@Date: 2022/3/9
@Author: ChenJk
*/
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    // 访问地址： http://locathost:8001/eduservice/teacher/findAll
    // 把service注入
    @Resource
    private EduTeacherService eduTeacherService;

    // login
    @PostMapping("/login")
    public ResultJson login(){
        return ResultJson.ok().data("token","admin");
    }

    // info
    @GetMapping("/info")
    public ResultJson info(){
        return ResultJson.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://guli-object.oss-cn-guangzhou.aliyuncs.com/avatar/c8010bb2-81f7-4826-a6c5-29286c8b5fc9.png");
    }
}

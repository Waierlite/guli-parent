package com.atguigu.oss.controller;

import com.atguigu.commonutils.ResultJson;
import com.atguigu.oss.service.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/*
@Date: 2022/3/10
@Author: ChenJk
*/
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Resource
    private OssService ossService;

    // 上传头像方法
    @PostMapping
    public ResultJson uploadOssFile(MultipartFile file){
        String url = ossService.uploadAvatar(file);
        return ResultJson.ok().data("url",url);
    }
}

package com.atguigu.oss.service;
/*
@Date: 2022/3/10
@Author: ChenJk
*/

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadAvatar(MultipartFile file);
}

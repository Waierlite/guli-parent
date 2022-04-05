package com.atguigu.vod.service;
/*
@Date: 2022/3/16
@Author: ChenJk
*/

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoToAliyun(MultipartFile file);

    boolean removeVideoById(String videoSourceId);

    boolean removeBatchVideo(List<String> videoList);
}

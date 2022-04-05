package com.atguigu.eduservice.client;
/*
@Date: 2022/3/18
@Author: ChenJk
*/

import com.atguigu.commonutils.ResultJson;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodClientFallbackMethod implements VodClient {
    @Override
    public ResultJson removeVideo(String videoId) {
        return ResultJson.error().message("删除视频出错了，请检查...");
    }

    @Override
    public ResultJson removeBatchVideo(List<String> videoList) {
        return ResultJson.error().message("批量删除视频出错了，请检查...");
    }
}

package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.ResultJson;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/*
@Date: 2022/3/16
@Author: ChenJk
*/
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Resource
    private VodService vodService;

    // 上传音视频到阿里云
    @PostMapping("uploadVideo")
    public ResultJson upLoadVideo(MultipartFile file) {
        // 返回上传的id
        String videoId = vodService.uploadVideoToAliyun(file);
        return ResultJson.ok().data("videoId", videoId);
    }

    // 根据视频id删除阿里云视频
    @DeleteMapping("removeVideo/{videoSourceId}")
    public ResultJson removeVideo(@PathVariable String videoSourceId){
        boolean flag = vodService.removeVideoById(videoSourceId);
        return flag ? ResultJson.ok() : ResultJson.error();
    }

    // 根据视频id批量删除阿里云视频
    @DeleteMapping("removeBatchVideo")
    public ResultJson removeBatchVideo(@RequestParam("videoList") List<String> videoList) {
        boolean flag = vodService.removeBatchVideo(videoList);
        return flag ? ResultJson.ok() : ResultJson.error();
    }

    // 根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public ResultJson getPlayAuth(@PathVariable String id){
        String playAuth = null;
        try {
            // 创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建获取视频地址request 和 response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request对象里面设置视频id
            request.setVideoId(id);
            // 调用初始化对象里面的方法，传递request，获取数据
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            // 获取response里面的播放凭证
            playAuth = response.getPlayAuth();
            System.out.println("PlayAuth: " + playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return ResultJson.ok().data("playAuth",playAuth);
    }
}

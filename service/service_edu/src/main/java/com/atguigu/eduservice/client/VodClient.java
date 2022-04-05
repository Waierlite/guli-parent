package com.atguigu.eduservice.client;

import com.atguigu.commonutils.ResultJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
@Date: 2022/3/17
@Author: ChenJk
*/
@Component
@FeignClient(name = "service-vod",fallback = VodClientFallbackMethod.class)  // 声明fallback实现类
public interface VodClient {
    // 注意补全调用方法的路径
    // @PathVariable注解一定要指定参数名称，否则出错
    @DeleteMapping("/eduvod/video/removeVideo/{videoId}")
    public ResultJson removeVideo(@PathVariable("videoId") String videoId);

    // 根据视频id批量删除阿里云视频
    @DeleteMapping("/eduvod/video/removeBatchVideo")
    public ResultJson removeBatchVideo(@RequestParam("videoList") List<String> videoList);
}

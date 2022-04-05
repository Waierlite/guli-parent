package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Resource
    private EduVideoService videoService;

    // 添加小节
    @PostMapping("addVideo")
    public ResultJson addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return ResultJson.ok();
    }

    // 删除小节
    @DeleteMapping("delVideo/{id}")
    public ResultJson delVideo(@PathVariable String id) {
        Boolean flag = videoService.removeVideo(id);
        return flag ? ResultJson.ok() : ResultJson.error();
    }

    @GetMapping("getVideoInfo/{videoId}")
    public ResultJson getVideoInfo(@PathVariable String videoId) {
        EduVideo video = videoService.getById(videoId);
        return ResultJson.ok().data("item", video);
    }

    // 修改小节 TODO
    @PostMapping("updateVideo")
    public ResultJson updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return ResultJson.ok();
    }
}


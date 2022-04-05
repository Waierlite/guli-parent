package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Resource
    private EduChapterService chapterService;

    // 课程大纲
    @GetMapping("getChapterVideo/{courseId}")
    public ResultJson getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.getChapterVideo(courseId);
        return ResultJson.ok().data("chapterList", chapterVoList);
    }

    // 根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public ResultJson getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return ResultJson.ok().data("chapter", eduChapter);
    }

    // 修改章节
    @PostMapping("updateChapter")
    public ResultJson updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return ResultJson.ok();
    }

    // 添加章节
    @PostMapping("addChapter")
    public ResultJson addChapter(@RequestBody EduChapter eduChapter) {
        boolean save = chapterService.save(eduChapter);
        return ResultJson.ok();
    }

    // 删除章节
    @DeleteMapping("delChapter/{chapterId}")
    public ResultJson delChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        return flag ? ResultJson.ok() : ResultJson.error();
    }
}


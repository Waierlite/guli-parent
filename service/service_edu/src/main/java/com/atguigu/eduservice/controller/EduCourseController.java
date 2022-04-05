package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.atguigu.eduservice.entity.vo.CoursePublishForm;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
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
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Resource
    private EduCourseService courseService;

    // 查询所有课程信息
    @GetMapping("getCourseList")
    public ResultJson getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return ResultJson.ok().data("list",list);
    }

    // 条件查询分页信息
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public ResultJson pageCourseCondition(@PathVariable long current,
                                           @PathVariable long limit,
                                           @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> pageCourse = courseService.pageConditionSelect(current,limit,courseQuery);
        // 将page对象放入data
        return ResultJson.ok().data("pageObject",pageCourse);
    }

    // 添加课程信息
    @PostMapping("addCourseInfo")
    public ResultJson addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String id = courseService.saveCourseInfoForm(courseInfoForm);
        return ResultJson.ok().data("courseId", id);
    }

    // 根据课程Id删除所有相关信息，包括课时中的视频 TODO
    @DeleteMapping("delCourseInfo/{courseId}")
    public ResultJson delCourseInfo(@PathVariable String courseId) {
        boolean flag = courseService.removeCourse(courseId);
        return flag ? ResultJson.ok() : ResultJson.error();
    }

    // 获取课程信息（需要关联简介表）
    @GetMapping("getCourseInfo/{courseId}")
    public ResultJson getCourseInfo(@PathVariable String courseId) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(courseId);
        return ResultJson.ok().data("courseInfo", courseInfoForm);
    }

    // 更新课程信息
    @PostMapping("updateCourseInfo")
    public ResultJson updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        courseService.updateCourseInfo(courseInfoForm);
        return ResultJson.ok();
    }

    // 根据课程Id查询确认信息
    @GetMapping("getCoursePublishInfo/{courseId}")
    public ResultJson getCoursePublishInfo(@PathVariable String courseId) {
        CoursePublishForm coursePublishForm = courseService.getCoursePublishForm(courseId);
        return ResultJson.ok().data("publishInfo", coursePublishForm);
    }

    // 课程的最终发布(status 字段的值从 Draft 改为 Normal)
    @PutMapping("publishCourseInfo/{courseId}")
    public ResultJson publishCourseInfo(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean flag = courseService.updateById(eduCourse);
        return flag ? ResultJson.ok() : ResultJson.error();
    }

}


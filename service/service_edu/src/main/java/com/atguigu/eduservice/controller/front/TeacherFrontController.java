package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
@Date: 2022/3/22
@Author: ChenJk
*/
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Resource
    private EduTeacherService teacherService;

    @Resource
    private EduCourseService courseService;

    // 分页查询讲师
    @GetMapping("/pageTeacherCondition/{current}/{limit}")
    public ResultJson pageTeacherCondition(@PathVariable long current,
                                           @PathVariable long limit) {
        // 创建page对象(使用baomidou的包进行封装page对象)
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        // 返回分页所有数据
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);

        return ResultJson.ok().data(map);
    }

    // 查询讲师详情功能
    @GetMapping("/getTeacherInfo/{teacherId}")
    public ResultJson getTeacherInfo(@PathVariable String teacherId){
        // 查询讲师信息
        EduTeacher teacherInfo = teacherService.getById(teacherId);
        // 查询讲师课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        return ResultJson.ok().data("teacherInfo",teacherInfo).data("courseList",courseList);
    }
}

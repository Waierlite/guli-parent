package com.atguigu.eduservice.controller.front;
/*
@Date: 2022/3/19
@Author: ChenJk
*/

import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Resource
    private EduCourseService courseService;

    @Resource
    private EduTeacherService teacherService;

    // 查询前8个热门课程，查询前4个名师
    @Cacheable(key = "'selectIndexList'", value = "index")  // 这个注解一般放在service层中的，这里为了演示直接放到了controller层上
    @ApiModelProperty("查询首页展示数据")
    @GetMapping("getIndexInfo")
    public ResultJson getFrontInfo() {
        // 按创建时间降序排序，查询课程前8个
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("gmt_create");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);
        // 按教师等级降序排序，查询讲师前4个
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("level");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);
        return ResultJson.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}

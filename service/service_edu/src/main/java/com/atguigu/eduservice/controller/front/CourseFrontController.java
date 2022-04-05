package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.CourseFrontInfoCommon;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.CourseFrontInfo;
import com.atguigu.eduservice.entity.vo.CourseFrontQuery;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/*
@Date: 2022/3/22
@Author: ChenJk
*/
@RestController
@RequestMapping("/eduservice/coursefront")
@Slf4j
public class CourseFrontController {

    @Resource
    private EduCourseService courseService;

    @Resource
    private EduChapterService chapterService;

    @Resource
    private OrderClient orderClient;

    // 分页查询 + 条件查询 课程列表
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public ResultJson pageTeacherCondition(@PathVariable long current,
                                           @PathVariable long limit,
                                           @RequestBody(required = false) CourseFrontQuery courseFrontQuery) {
        // 创建page对象(使用baomidou的包进行封装page对象)
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        // 返回分页所有数据
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontQuery);

        return ResultJson.ok().data(map);
    }

    // 课程详情的方法
    @GetMapping("getCourseFrontInfo/{courseId}")
    public ResultJson getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request){
        // 根据课程id，使用自定义sql多表查询
        CourseFrontInfo courseFrontInfo = courseService.getBaseCourseInfo(courseId);
        // 根据课程id，查询章节和小节
        List<ChapterVo> chapterVoList = chapterService.getChapterVideo(courseId);
        // 根据request获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean isBuy = false;
        String avatar = "";
        // 判断用户是否登录
        if (!StringUtils.isEmpty(memberId)){
            isBuy = orderClient.isBuyCourse(courseId, memberId);
            avatar = JwtUtils.getMemberAvatarByJwtToken(request);
        }
        return ResultJson.ok().data("courseFrontInfo",courseFrontInfo).data("chapterVoList",chapterVoList).data("isBuy",isBuy).data("userAvatar",avatar);
    }

    // 根据课程id查询课程所有信息
    @GetMapping("getCourseInfo/{courseId}")
    public CourseFrontInfoCommon getCourseInfo(@PathVariable("courseId") String courseId){
        CourseFrontInfo courseInfo = courseService.getBaseCourseInfo(courseId);
        CourseFrontInfoCommon courseFrontInfoCommon = new CourseFrontInfoCommon();
        BeanUtils.copyProperties(courseInfo,courseFrontInfoCommon);
        return courseFrontInfoCommon;
    }

}

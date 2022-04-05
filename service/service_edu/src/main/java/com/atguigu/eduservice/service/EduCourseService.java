package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfoForm(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishForm getCoursePublishForm(String id);

    boolean removeCourse(String courseId);

    Page<EduCourse> pageConditionSelect(long current, long limit, CourseQuery courseQuery);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontQuery courseFrontQuery);

    CourseFrontInfo getBaseCourseInfo(String courseId);
}

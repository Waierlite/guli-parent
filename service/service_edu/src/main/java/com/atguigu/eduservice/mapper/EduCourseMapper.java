package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseFrontInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishForm getCoursePublishForm(String courseId);

    int deleteCourse(String courseId);

    CourseFrontInfo getCourseFrontInfo(String courseId);
}

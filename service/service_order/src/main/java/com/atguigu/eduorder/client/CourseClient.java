package com.atguigu.eduorder.client;

import com.atguigu.commonutils.CourseFrontInfoCommon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
@Date: 2022/3/25
@Author: ChenJk
*/
@Component
@FeignClient(name = "service-edu")
public interface CourseClient {

    @GetMapping("/eduservice/coursefront/getCourseInfo/{courseId}")
    public CourseFrontInfoCommon getCourseInfo(@PathVariable("courseId") String courseId);
}

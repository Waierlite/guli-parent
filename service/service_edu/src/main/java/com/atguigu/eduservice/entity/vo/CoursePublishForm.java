package com.atguigu.eduservice.entity.vo;
/*
@Date: 2022/3/14
@Author: ChenJk
*/

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublishForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}

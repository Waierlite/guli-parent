package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
@Date: 2022/3/15
@Author: ChenJk
*/
@Data
public class CourseQuery {
    @ApiModelProperty(value = "查询课程名称",example = "课程名称，模糊查询")
    private String title;

    @ApiModelProperty(value = "查询发布状态",example = "状态: Normal(已发布) Draft(未发布)")
    private String status;

    @ApiModelProperty(value = "查询开始时间",example = "2022-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询开始时间",example = "2022-12-01 10:10:10")
    private String end;
}

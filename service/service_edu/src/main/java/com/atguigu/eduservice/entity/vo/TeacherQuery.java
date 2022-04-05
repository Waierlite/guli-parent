package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
@Date: 2022/3/4
@Author: ChenJk
*/
@Data
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "等级：1 高级讲师 2 首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2022-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询开始时间",example = "2022-12-01 10:10:10")
    private String end;
}

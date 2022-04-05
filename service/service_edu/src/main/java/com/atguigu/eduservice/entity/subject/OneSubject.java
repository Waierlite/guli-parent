package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

/*
@Date: 2022/3/12
@Author: ChenJk
*/
@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children;
}

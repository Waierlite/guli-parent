package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.List;

/*
@Date: 2022/3/13
@Author: ChenJk
*/
@Data
public class ChapterVo {
    private String id;

    private String title;

    private List<VideoVo> children;
}

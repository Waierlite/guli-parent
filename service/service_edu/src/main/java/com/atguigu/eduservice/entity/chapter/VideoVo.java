package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

/*
@Date: 2022/3/13
@Author: ChenJk
*/
@Data
public class VideoVo {
    private String id;

    private String title;

    private Integer isFree;

    private String videoSourceId;
}

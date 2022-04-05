package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
public interface EduVideoService extends IService<EduVideo> {

    Boolean removeVideo(String id);
}

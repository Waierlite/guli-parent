package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-02
 */
public interface EduTeacherService extends IService<EduTeacher> {
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}

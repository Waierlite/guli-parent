package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Resource
    private EduSubjectService subjectService;

    // 上传excel文件进行读取添加课程
    @PostMapping("import")
    public ResultJson addSubject(MultipartFile file){
        subjectService.saveSubject(file);
        return ResultJson.ok();
    }

    // 课程分类列表（树形）
    @GetMapping("getAllSubject")
    public ResultJson getAllSubject(){
        // list的泛型是一级分类，因为一级分类中包含二级分类
        List<OneSubject> list = subjectService.getAllSubject();
        return ResultJson.ok().data("list",list);
    }


}


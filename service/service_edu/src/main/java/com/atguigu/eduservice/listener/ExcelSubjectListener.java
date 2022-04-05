package com.atguigu.eduservice.listener;
/*
@Date: 2022/3/12
@Author: ChenJk
*/

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class ExcelSubjectListener extends AnalysisEventListener<SubjectData> {
    // 因为ExcelSubjectListener不能交给spring进行关联，需要自己new，不能注入其他对象
    // 所以这里需要传入service对象才能对数据库进行操作（在service创建该监听器的时候传入this）
    private EduSubjectService subjectService;

    public ExcelSubjectListener(EduSubjectService eduSubjectService) {
        this.subjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new RuntimeException("20001,文件数据为空");
        }
        // 判断一级分类是否存在
        EduSubject existOneSubject = this.existOneSubject(subjectData.getOneSubjectName());
        if (existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        // 获取一级分类的id，作为二级分类的prentId
        String prentId = existOneSubject.getId();

        // 判断二级分类是否存在
        EduSubject existTwoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(),prentId);
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(prentId);
            subjectService.save(existTwoSubject);
        }
    }

    // 判断一级分类不能重复添加
    private EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }

    // 判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name, String prentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", prentId);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

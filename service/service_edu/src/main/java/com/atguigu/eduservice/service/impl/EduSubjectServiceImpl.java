package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.ExcelSubjectListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file) {
        try {
            InputStream input = file.getInputStream();
            EasyExcel.read(input, SubjectData.class,new ExcelSubjectListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 课程分类列表（树形）
    @Override
    public List<OneSubject> getAllSubject() {
        // 查询一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> eduSubjectsOne = baseMapper.selectList(wrapperOne);

        // 查询二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> eduSubjectsTwo = baseMapper.selectList(wrapperTwo);

        // 最终的封装集合
        List<OneSubject> finalList = new ArrayList<>();

        // 封装一级分类
        for (EduSubject one : eduSubjectsOne){
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(one,oneSubject);

            // 封装二级分类
            String oneId = one.getId();
            List<TwoSubject> list = new ArrayList<>();
            // 查询二级分类封装进一级分类中
            for (EduSubject two : eduSubjectsTwo){
                if(oneId.equals(two.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(two,twoSubject);
                    list.add(twoSubject);
                }
            }
            oneSubject.setChildren(list);
            finalList.add(oneSubject);
        }
        return finalList;
    }
}

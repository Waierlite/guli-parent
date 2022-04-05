package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.*;
import com.atguigu.eduservice.entity.vo.*;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Resource
    private EduCourseDescriptionMapper courseDescriptionMapper;

    @Resource
    private EduVideoMapper videoMapper;

    @Resource
    private VodClient vodClient;

    @Resource
    private EduCourseMapper courseMapper;

    @Override
    public String saveCourseInfoForm(CourseInfoForm courseInfoForm) {
        // 向课程表添加信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0){
            throw new RuntimeException("课程信息添加失败，请检查...");
        }

        // 向课程简介表添加信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        // 获取CourseID
        String cid = eduCourse.getId();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        int insert1 = courseDescriptionMapper.insert(eduCourseDescription);
        if (insert1 <= 0){
            throw new RuntimeException("课程简介信息添加失败，请检查...");
        }

        return cid;
    }

    @Override
    public CourseInfoForm getCourseInfoById(String courseId) {
        // 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        // 查询简介表
        EduCourseDescription eduCourseDescription = courseDescriptionMapper.selectById(courseId);
        // 信息写入CourseInfoForm类进行返回
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        BeanUtils.copyProperties(eduCourseDescription,courseInfoForm);
        return courseInfoForm;
    }

    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.updateById(eduCourse);
        if (insert <= 0){
            throw new RuntimeException("课程信息添加失败，请检查...");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        // 获取CourseID
        String cid = eduCourse.getId();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        int insert1 = courseDescriptionMapper.updateById(eduCourseDescription);
        if (insert1 <= 0){
            throw new RuntimeException("课程简介信息添加失败，请检查...");
        }
    }

    @Override
    public CoursePublishForm getCoursePublishForm(String id) {
        // 调用mapper自定义方法必须用baseMapper调用
        CoursePublishForm coursePublishForm = baseMapper.getCoursePublishForm(id);
        return coursePublishForm;
    }

    // 递归删除:服务器上视频>课时信息>章节信息>简介信息>课程信息
    @Override
    public boolean removeCourse(String courseId) {
        // 删除视频需要获取video表中的 video_source_id List
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = videoMapper.selectList(wrapper);
        // 将video_source_id放入list集合中
        ArrayList<String> videoSourceIdList = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos){
            videoSourceIdList.add(eduVideo.getVideoSourceId());
        }
        int index = 0;
        if (videoSourceIdList.size() > 0){
            // 调用vod的removeBatchVideo()批量删除进行删除
            vodClient.removeBatchVideo(videoSourceIdList);
            // 使用自定义SQL进行多表关联删除
            index = baseMapper.deleteCourse(courseId);
        }
        return index > 0;
    }

    @Override
    public Page<EduCourse> pageConditionSelect(long current, long limit, CourseQuery courseQuery) {
        // 创建page对象(使用baomidou的包进行封装page对象)
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        // 创建wrapper对象
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 判断条件是否为空，不为空就拼接条件
        if (courseQuery != null){
            String title = courseQuery.getTitle();
            String status = courseQuery.getStatus();
            String begin = courseQuery.getBegin();
            String end = courseQuery.getEnd();
            if (!StringUtils.isEmpty(title)) {
                wrapper.like("title",title);
            }
            if (!StringUtils.isEmpty(status)) {
                wrapper.eq("status",status);
            }
            if (!StringUtils.isEmpty(begin)) {
                wrapper.gt("gmt_create",begin);
            }
            if (!StringUtils.isEmpty(end)) {
                wrapper.lt("gmt_modified",end);
            }
        }
        // 按创建时间排序
        wrapper.orderByDesc("gmt_create");
        // 调用分页函数
        courseMapper.selectPage(pageCourse,wrapper);
        return pageCourse;
    }

    // 分页 + 条件 查询
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontQuery courseFrontQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 判断条件是否为空，不为空就拼接条件
        if (courseFrontQuery != null){
            if (!StringUtils.isEmpty(courseFrontQuery.getSubjectParentId())) { // 一级分类
                wrapper.eq("subject_parent_id",courseFrontQuery.getSubjectParentId());
            }
            if (!StringUtils.isEmpty(courseFrontQuery.getSubjectId())) {    // 二级分类
                wrapper.eq("subject_id",courseFrontQuery.getSubjectId());
            }
            if (!StringUtils.isEmpty(courseFrontQuery.getBuyCountSort())) { // 销量排序
                wrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(courseFrontQuery.getBuyCountSort())) { // 时间排序
                wrapper.orderByDesc("gmt_create");
            }
            if (!StringUtils.isEmpty(courseFrontQuery.getPriceSort())) { // 时间排序
                wrapper.orderByDesc("price");
            }
        }
        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        // 把分页数据获取出来，放到map集合中
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    // 根据课程id，查询课程信息
    @Override
    public CourseFrontInfo getBaseCourseInfo(String courseId) {
        return baseMapper.getCourseFrontInfo(courseId);
    }


}

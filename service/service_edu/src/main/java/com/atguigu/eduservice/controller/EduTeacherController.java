package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    // 注入Service
    @Resource
    private EduTeacherService eduTeacherService;

    // 查询讲师表所有数据
    // restful风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public ResultJson findAllTeacher(){
        // 调用service的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacherService.list(null);
        return ResultJson.ok().data("items",list);
    }

    // 逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/del/{id}")
    public ResultJson deleteTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)
                                     @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        return flag ? ResultJson.ok() : ResultJson.error();
    }

    // 条件查询分页讲师信息
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public ResultJson pageTeacherCondition(@PathVariable long current,
                                           @PathVariable long limit,
                                           @RequestBody(required = false) TeacherQuery teacherQuery){
        // 创建page对象(使用baomidou的包进行封装page对象)
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        // 创建wrapper对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 判断条件是否为空，不为空就拼接条件
        if (teacherQuery != null){
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name",name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level",level);
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
        eduTeacherService.page(pageTeacher,wrapper);
        // 将page对象放入data
        return ResultJson.ok().data("pageObject",pageTeacher);
    }

    // 添加讲师接口
    @PostMapping("/add")
    public ResultJson addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? ResultJson.ok() : ResultJson.error();
    }

    // 根据讲师ID查询
    @GetMapping("/get/{id}")
    public ResultJson getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return ResultJson.ok().data("teacher", eduTeacher);
    }

    // 讲师信息修改
    @PutMapping("/update")
    public ResultJson updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        return flag ? ResultJson.ok() : ResultJson.error();
    }
}


package com.atguigu.eduservice.entity.excel;
/*
@Date: 2022/3/12
@Author: ChenJk
*/

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(value = "一级分类",index = 0)
    private String oneSubjectName;

    @ExcelProperty(value = "二级分类",index = 1)
    private String twoSubjectName;
}

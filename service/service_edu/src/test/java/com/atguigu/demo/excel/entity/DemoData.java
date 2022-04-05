package com.atguigu.demo.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/*
@Date: 2022/3/11
@Author: ChenJk
*/
@Data
public class DemoData {
    // 设置excel表头名称 value，还有列数 index
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}

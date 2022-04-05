package com.atguigu.demo.excel;
/*
@Date: 2022/3/11
@Author: ChenJk
*/

import com.alibaba.excel.EasyExcel;
import com.atguigu.demo.excel.entity.DemoData;
import com.atguigu.demo.excel.listener.ExcelListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    // 创建方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for(int i = 0;i< 10; i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("yuyu"+i);
            list.add(data);
        }
        return list;
    }

    public static void main(String[] args) {
        // 实现excel写的操作
        // 设置写入文件夹地址的excel文件名称
        String fileUrl = "D:/testEasyExcel.xlsx";

        // 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(fileUrl,DemoData.class).sheet("学生列表").doWrite(getData());
    }

    @Test
    public void excelRead(){
        String fileUrl = "D:/testEasyExcel.xlsx";
        EasyExcel.read(fileUrl,DemoData.class,new ExcelListener()).sheet("学生列表").doRead();
    }

}

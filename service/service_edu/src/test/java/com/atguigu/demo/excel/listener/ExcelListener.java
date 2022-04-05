package com.atguigu.demo.excel.listener;
/*
@Date: 2022/3/12
@Author: ChenJk
*/

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.demo.excel.entity.DemoData;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    @Override
    // 读取表头内容
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("excel head:" + headMap);
    }

    // 一行一行读取excel内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("excel context:" + demoData);
    }

    // 读取完成后的动作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

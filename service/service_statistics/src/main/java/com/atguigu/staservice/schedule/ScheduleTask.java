package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/*
@Date: 2022/3/30
@Author: ChenJk
*/
@Component
@Slf4j
public class ScheduleTask {

    @Resource
    private StatisticsDailyService statisticsDailyService;

    // 0/5 * * * * ? 表示每个5秒执行一次这个方法
/*    @Scheduled(cron = "0/5 * * * * ?")
    public void task1(){
        log.info("---------{} task1执行了---------",new Date().toString());
    }*/

    // 每天凌晨一点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        // 统计昨天的注册人数
        statisticsDailyService.countRegister(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}

package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-29
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    boolean countRegister(String date);

    Map<String, Object> getShowData(String type, String begin, String end);
}

package com.atguigu.staservice.controller;


import com.atguigu.commonutils.ResultJson;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Resource
    private StatisticsDailyService staService;

    // 统计某一天注册人数，生成数据
    @GetMapping("countRegister/{date}")
    public ResultJson countRegister(@PathVariable String date){
        boolean flag = staService.countRegister(date);
        return ResultJson.ok();
    }

    // Echarts折线图需要x轴和y轴两组数组数据
    @GetMapping("showData/{type}/{begin}/{end}")
    public ResultJson showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return ResultJson.ok().data(map);
    }
}


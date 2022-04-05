package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.ResultJson;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-29
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Resource
    private UcenterClient ucenterClient;

    @Override
    public boolean countRegister(String date) {
        try {
            // 远程调用得到某一天注册人数
            ResultJson resultJson = ucenterClient.countRegister(date);
            int registerNum = (Integer) resultJson.getData().get("count");
            int loginNum = RandomUtils.nextInt(0,200); //TODO
            int videoViewNum = RandomUtils.nextInt(0,200); //TODO
            int courseNum = RandomUtils.nextInt(0,200); //TODO

            QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
            wrapper.eq("date_calculated",date);  //
            Integer count = baseMapper.selectCount(wrapper);

            // 将数据添加进入数据库统计分析表中
            StatisticsDaily statisticsDaily = new StatisticsDaily();
            statisticsDaily.setRegisterNum(registerNum);
            statisticsDaily.setLoginNum(loginNum);
            statisticsDaily.setVideoViewNum(videoViewNum);
            statisticsDaily.setCourseNum(courseNum);
            statisticsDaily.setDateCalculated(date); // 统计日期

            // 当统计日期是同一天时只更新数据不进行新增
            if ( count > 0 ){
                // 根据wrapper条件进行修改
                baseMapper.update(statisticsDaily,wrapper);
            }else {
                // 新增数据行
                baseMapper.insert(statisticsDaily);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);

        // 返回数据
        // 前端要求数据json数组，对应后端java代码是list集合
        // 创建两个list集合，一个日期list，一个数量list
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        // 遍历查询所有数据list集合，进行封装
        for (StatisticsDaily one : list){
            dateCalculatedList.add(one.getDateCalculated());
            // 判断类型
            switch (type){
                case "login_num":
                    numList.add(one.getLoginNum());
                    break;
                case "register_num":
                    numList.add(one.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(one.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(one.getCourseNum());
                    break;
            }
        }
        // 将封装后的list集合放入map中进行返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("dateCalculatedList",dateCalculatedList);
        map.put("numList",numList);

        return map;
    }
}

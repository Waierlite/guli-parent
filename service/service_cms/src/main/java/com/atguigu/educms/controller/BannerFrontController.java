package com.atguigu.educms.controller;


import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.atguigu.commonutils.ResultJson;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前台Banner显示
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {
    @Resource
    private CrmBannerService bannerService;

    @Cacheable(key = "'selectBannerList'", value = "banner")
    @ApiOperation(value = "获取所有banner")
    @GetMapping("getAllBanner")
    public ResultJson index() {
        // 根据Id进行降序排序，显示排序之后的两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // last方法可以在sql后面拼接语句
        wrapper.last("limit 2");
        List<CrmBanner> bannerList = bannerService.list(wrapper);
        return ResultJson.ok().data("bannerList", bannerList);
    }
}


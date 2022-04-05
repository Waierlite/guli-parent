package com.atguigu.educms.controller;


import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.atguigu.commonutils.ResultJson;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 后台管理接口
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {
    @Resource
    private CrmBannerService bannerService;

    // 分页查询Banner
    @ApiOperation(value = "获取Banner分页列表")
    @PostMapping("pageBanner/{current}/{limit}")
    public ResultJson pageBanner(@PathVariable long current, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(current,limit);
        bannerService.page(pageBanner,null);
        return ResultJson.ok().data("items",pageBanner);
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public ResultJson get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return ResultJson.ok().data("item", banner);
    }
    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public ResultJson save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return ResultJson.ok();
    }
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public ResultJson updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return ResultJson.ok();
    }
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public ResultJson remove(@PathVariable String id) {
        bannerService.removeById(id);
        return ResultJson.ok();
    }

}


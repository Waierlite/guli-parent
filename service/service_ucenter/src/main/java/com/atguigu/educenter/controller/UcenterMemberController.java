package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ResultJson;
import com.atguigu.commonutils.UcenterMemberCommon;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Resource
    private UcenterMemberService memberService;

    // 登录
    @ApiOperation("会员登录")
    @PostMapping("login")
    public ResultJson login(@RequestBody LoginVo loginVo) {
        /**
         * 实现单点登录的三种方式：
         *  1.使用session广播
         *  2.使用cookie + redis
         *  3.使用token
         *
         * 以下登录使用第三种方式，使用jwt生成token
         */
        String token = memberService.login(loginVo);
        return ResultJson.ok().data("token", token);
    }

    // 注册
    @PostMapping("register")
    public ResultJson register(@RequestBody RegisterVo registerVo) {
        boolean flag = memberService.register(registerVo);
        return flag ? ResultJson.ok() : ResultJson.error();
    }

    // 根据登录成功返回的token获取用户信息
    @GetMapping("getUserInfo")
    public ResultJson getUserInfo(HttpServletRequest request) {
        if (request == null) return ResultJson.error().message("request为空");
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 根据用户id查询数据库
        UcenterMember member = memberService.getById(memberId);
        return ResultJson.ok().data("userInfo", member);
    }

    // 根据用户id获取用户信息
    @GetMapping("getUserById/{id}")
    public UcenterMemberCommon getUserById(@PathVariable("id") String id) {
        UcenterMember member = memberService.getById(id);
        UcenterMemberCommon ucenterMemberCommon = new UcenterMemberCommon();
        BeanUtils.copyProperties(member, ucenterMemberCommon);
        return ucenterMemberCommon;
    }

    // 查询某一天注册人数
    @GetMapping("countRegister/{date}")
    public ResultJson countRegister(@PathVariable String date){
        int count = memberService.countRegister(date);
        return ResultJson.ok().data("count",count);
    }
}


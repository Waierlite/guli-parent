package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-25
 */
@RestController
@RequestMapping("/eduorder/order")
public class TOrderController {
    @Resource
    private TOrderService orderService;

    // 生成订单
    @PostMapping("createOrder/{courseId}")
    public ResultJson createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 判断用户是否登录
        if (StringUtils.isEmpty(memberId)) {
            return ResultJson.error().message("用户登录后才能购买。");
        }
        // 创建订单，返回订单号
        String orderNo = orderService.createOrder(courseId,memberId);
        return ResultJson.ok().data("orderNo",orderNo);
    }

    // 根据订单号(orderNo)查询订单信息
    @GetMapping("getOrderInfo/{orderNo}")
    public ResultJson getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = orderService.getOne(wrapper);
        return ResultJson.ok().data("item",order);
    }

    // 查询课程是否被用户购买
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1); // 支付状态，1为已支付
        int count = orderService.count(wrapper);
        // count大于0则返回true
        return count > 0;
    }
}


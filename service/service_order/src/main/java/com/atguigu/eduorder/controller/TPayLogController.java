package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduorder.service.TPayLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-25
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class TPayLogController {
    @Resource
    private TPayLogService payLogService;

    // 生成微信支付二维码接口
    @GetMapping("/createCode/{orderNo}")
    public ResultJson createCode(@PathVariable String orderNo){
        // 返回信息，包含二维码地址，还有其他信息
        Map map = payLogService.createCode(orderNo);
        return ResultJson.ok().data(map);
    }

    // 查询订单状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public ResultJson queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map != null) {
            System.out.println("***查询状态Map集合: " + map);
            // 如果map不为空，通过map获取订单状态
            if (map.get("trade_state").equals("SUCCESS")){
                // 添加记录到支付表，更新订单表订单状态
                payLogService.updateOrderStatus(map);
                return ResultJson.ok().message("支付成功。");
            }
        }else {
            return ResultJson.error().message("支付失败。");
        }
        return ResultJson.ok().code(25000).message("支付中。");
    }

}


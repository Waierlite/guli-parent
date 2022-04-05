package com.atguigu.edumsm.controller;

import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.atguigu.commonutils.ResultJson;
import com.atguigu.edumsm.service.MsmService;
import com.atguigu.edumsm.utils.RandomUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
@Date: 2022/3/19
@Author: ChenJk
*/
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    
    @GetMapping(value = "/send/{phone}")
    public ResultJson send(@PathVariable String phone) {
        // 从redis中获取验证码，如果获取到直接返回（避免短时间重复发送）
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return ResultJson.ok();
        // 从redis获取不到，进行阿里云发送
        // 使用工具类生成随机值，传递阿里云进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        // 调用service发送短信的方法
        boolean isSend = msmService.send(phone, "SMS_180051135", param);
        // 将发送成功的验证码放到redis里面，设置5分超时时间
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return ResultJson.ok();
        } else {
            return ResultJson.error().message("发送短信失败");
        }
    }

    @GetMapping(value = "/sendMail/{mail}")
    public ResultJson sendMail(@PathVariable String mail) {
        // 从redis中获取验证码，如果获取到直接返回（避免短时间重复发送）
        String code = redisTemplate.opsForValue().get(mail);
        if(!StringUtils.isEmpty(code)) return ResultJson.ok();
        // 从redis获取不到，进行邮箱发送
        // 使用工具类生成随机值，使用邮箱进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        // 调用service发送短信的方法
        boolean isSend = msmService.sendMail(mail,  param);
        // 将发送成功的验证码放到redis里面，设置5分超时时间
        if(isSend) {
            redisTemplate.opsForValue().set(mail, code,5, TimeUnit.MINUTES);
            return ResultJson.ok();
        } else {
            return ResultJson.error().message("发送短信失败");
        }
    }

}

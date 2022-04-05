package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWXUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/*
@Date: 2022/3/21
@Author: ChenJk
*/
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Resource
    private UcenterMemberService memberService;

    // 1.生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl %s相当于占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url进行URLEncoder编码
        String redirect_url = ConstantWXUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 使用String.format进行拼接参数
        String url = String.format(baseUrl, ConstantWXUtils.WX_OPEN_APP_ID, redirect_url, "atguigu");

        // redirect重定向到请求微信地址里面
        return "redirect:" + url;
    }

    // 获取扫描用户的信息，添加数据
    @GetMapping("callback")
    public String callback(String code, String state) {
        // 1.获取code值，临时票据，类似于验证码
        System.out.println("code: " + code);
        // 2.拿着code请求微信固定的地址
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        // 拼接三个参数：id 秘钥 code
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWXUtils.WX_OPEN_APP_ID,
                ConstantWXUtils.WX_OPEN_APP_SECRET,
                code
        );
        // 请求这个拼接好的地址，得到返回两个值，access_token 和 openid
        // 使用httpclient发送请求
        String accessTokenInfo = null;

        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo: " + accessTokenInfo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取access_token失败");
        }
        // 使用json转换工具Gson
        Gson gson = new Gson();
        HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
        String openid = (String) mapAccessToken.get("openid");
        String access_token = (String) mapAccessToken.get("access_token");

        // 根据openid查询数据库，如果以存在就不注册
        UcenterMember member = memberService.getByOpenid(openid);
        if (member == null) {
            //3.用 openid 和 access_token 再次访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, access_token);
            // 使用httpclient发送请求
            String userInfo = null;

            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userInfo: " + userInfo);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("获取用户信息失败");
            }
            // 获取用户信息
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname"); // 昵称
            String headimgurl = (String) userInfoMap.get("headimgurl"); // 头像

            member = new UcenterMember();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            // 注册用户信息
            memberService.save(member);
        }
        String jwtToken = JwtUtils.getJwtToken(member.getId(),member.getNickname(),member.getAvatar());
        // 最后：登录后返回首页面
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}

package com.atguigu.edumsm.service.impl;
/*
@Date: 2022/3/19
@Author: ChenJk
*/

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.edumsm.service.MsmService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String sms_180051135, Map<String, Object> param) {
        if (StringUtils.isEmpty(phone)) return false;
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(
                "<your-region-id>",          // 地域ID
                "<your-access-key-id>",   // RAM账号的AccessKey ID
                "<your-access-key-secret>");   // RAM账号Access Key Secret

        /** 使用 STS Token 方式，注意：直接使用STS Token时，您需要自行维护STS Token的周期性更新。
         DefaultProfile profile = DefaultProfile.getProfile(
         "<your-region-id>",          // 地域ID
         "<your-access-key-id>",      // 以STS开头的AccessKey ID
         "<your-access-key-secret>",  // RAM账号Access Key Secret
         "<your-sts-token>");         // STS Token
         **/
        IAcsClient client = new DefaultAcsClient(profile);

        // 创建API请求并设置参数(固定)
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 设置发送相关的参数
        request.putQueryParameter("PhoneNumber",phone);  // 手机号
        request.putQueryParameter("SignName","谷粒学苑在线教育平台");  // 申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_1880051135");  // 申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));  // 验证码数据，转换json数据传递

        // 最终发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean sendMail(String mail, Map<String, Object> param) {
        if (StringUtils.isEmpty(mail)) return false;
        //发件人的邮箱和授权码
        String myEmailAccount = "waierlite@163.com";  // 发送的邮箱
        String myEmailPassword = "JQCUVFUAMJAUIWST";  // 邮箱授权码

        try {
            HtmlEmail email=new HtmlEmail();//创建一个HtmlEmail实例对象
            // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
            // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com    腾讯: smtp.qq.com
            email.setHostName("smtp.163.com");

            email.setCharset("utf-8"); //  设置发送的字符类型
            email.addTo(mail);  //  设置收件人
            email.setFrom(myEmailAccount,"谷粒学苑");  //发送人的邮箱为自己的，用户名可以随便填
            email.setAuthentication(myEmailAccount,myEmailPassword);  //设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)

            email.setSubject("[谷粒学苑]验证码");  //设置发送主题
            email.setMsg("您本次的验证码: " + param.get("code") + "\n该验证码于5分钟后失效，请尽快使用。");  //设置发送内容
            email.send();  //进行发送
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return false;
    }
}

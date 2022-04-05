package com.atguigu.edumsm.service;
/*
@Date: 2022/3/19
@Author: ChenJk
*/

import java.util.Map;

public interface MsmService {
    boolean send(String phone, String sms_180051135, Map<String, Object> param);

    boolean sendMail(String mail, Map<String, Object> param);
}

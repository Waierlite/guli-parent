package com.atguigu.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登录对象", description = "登录对象")
public class LoginVo {
    @ApiModelProperty(value = "电子邮箱")
    private String mail;

    @ApiModelProperty(value = "密码")
    private String password;
}
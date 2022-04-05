package com.atguigu.commonutils;
/*
@Date: 2022/3/4
@Author: ChenJk
*/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// 统一返回结果的类
@Data
public class ResultJson {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    // 构造方法私有化
    private ResultJson() {
    }

    // 成功静态方法
    public static ResultJson ok(){
        ResultJson resultJson = new ResultJson();
        resultJson.setSuccess(true);
        resultJson.setCode(ResultCode.SUCCESS);
        resultJson.setMessage("成功");
        return resultJson;
    }

    // 失败静态方法
    public static ResultJson error(){
        ResultJson resultJson = new ResultJson();
        resultJson.setSuccess(false);
        resultJson.setCode(ResultCode.ERROR);
        resultJson.setMessage("失败");
        return resultJson;
    }

    public ResultJson success(Boolean success){
        this.setSuccess(true);
        return this;
    }

    public ResultJson message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultJson code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultJson data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public ResultJson data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}

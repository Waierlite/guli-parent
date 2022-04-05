package com.atguigu.oss.service.impl;
/*
@Date: 2022/3/10
@Author: ChenJk
*/

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvatar(MultipartFile file) {
        // 获取工具类中的常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            /**
             *  创建OSS实例
             *  第一个参数 地域节点名称
             *  第二个参数 秘钥id
             *  第三个参数 秘钥
             */
            OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);

            // 上传文件流
            InputStream inputStream = file.getInputStream();

            /**
             *  调用oss方法实现文件上传
             *  第一个参数 bucket 名称
             *  第二个参数 上传到oss文件路径和文件名称 avatar/test.jpg
             *  第三个参数 上传文件输入流
             */
            String originalFilename = file.getOriginalFilename();
            // System.out.println(originalFilename);
            String filename = UUID.randomUUID().toString();
            String filetype = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = filename + filetype;
            String fileUrl = "avatar/" + newFilename;
            ossClient.putObject(bucketName, fileUrl, inputStream);

            // 关闭ossClient
            ossClient.shutdown();

            // 把上传后的文件url返回
            // https://guli-object.oss-cn-guangzhou.aliyuncs.com/avatar/test.jpg
            return "https://" + bucketName + "." + endPoint + "/" + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

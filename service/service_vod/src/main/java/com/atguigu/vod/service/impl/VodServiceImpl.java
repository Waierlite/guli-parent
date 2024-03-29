package com.atguigu.vod.service.impl;
/*
@Date: 2022/3/16
@Author: ChenJk
*/

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.atguigu.vod.utils.ConstantVodUtils.ACCESS_KEY_ID;
import static com.atguigu.vod.utils.ConstantVodUtils.ACCESS_KEY_SECRET;
import static com.atguigu.vod.utils.InitVodClient.initVodClient;

@Service
public class VodServiceImpl implements VodService {

    @Override
    // 流式上传接口
    public String uploadVideoToAliyun(MultipartFile file) {
        String videoId = null;
        try {
            String accessKeyId = ACCESS_KEY_ID;
            String accessKeySecret = ACCESS_KEY_SECRET;
            String fileName = file.getOriginalFilename();                           // 上传文件原始名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));     // 上传之后文件名称(去掉文件格式后缀.mp4)
            InputStream inputStream = file.getInputStream();                        // 上传文件输入流
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            //request.setShowWaterMark(true);
            /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
            //request.setCallback("http://callback.sample.com");
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            videoId = null;

            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId=response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId=response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }

    @Override
    // 根据id删除阿里云服务器中的音视频
    public boolean removeVideoById(String videoSourceId) {
        // 初始化client对象
        DefaultAcsClient client = initVodClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 创建删除用的request response
        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response = new DeleteVideoResponse();
        // 支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoSourceId);
        try {
            // 执行删除操作
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            return false;
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
        return true;
    }

    @Override
    // 批量删除
    public boolean removeBatchVideo(List<String> videoSourceIdList) {
        if (videoSourceIdList.size() > 0){
            // 初始化client对象
            DefaultAcsClient client = initVodClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            // 创建删除用的request response
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            // 将 list集合 转换为 String数组 并且用 ”,” 分割
            String ids = StringUtils.join(videoSourceIdList.toArray(),",");
            // 支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(ids);
            try {
                // 执行删除操作
                response = client.getAcsResponse(request);
            } catch (Exception e) {
                System.out.print("ErrorMessage = " + e.getLocalizedMessage());
                return false;
            }
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        }
        return true;
    }
}

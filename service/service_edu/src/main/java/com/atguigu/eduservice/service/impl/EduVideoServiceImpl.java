package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Resource
    private VodClient vodClient;

    // 根据课时id删除 课时信息 和 服务器上的视频
    @Override
    public Boolean removeVideo(String id) {
        try {
            // 通过课时id查询video_source_id
            EduVideo eduVideo = baseMapper.selectById(id);
            String videoSourceId = eduVideo.getVideoSourceId();
            // 删除数据库 和 阿里云服务器上的视频
            if (!StringUtils.isEmpty(videoSourceId)){
                vodClient.removeVideo(videoSourceId);
            }
            baseMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

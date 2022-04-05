package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-12
 */
@Service
@Slf4j
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Resource
    private EduVideoService videoService;

    // 封装章节信息
    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        // 查询一级分类
        QueryWrapper<EduChapter> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("course_id",courseId);
        List<EduChapter> eduChaptersOne = baseMapper.selectList(wrapperOne);

        // 查询二级分类
        QueryWrapper<EduVideo> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.eq("course_id",courseId);
        List<EduVideo> eduVideosTwo = videoService.list(wrapperTwo);

        // 最终的封装集合
        List<ChapterVo> finalList = new ArrayList<>();

        // 封装一级分类
        for (EduChapter one : eduChaptersOne){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(one, chapterVo);

            // 封装二级分类
            String oneId = one.getId();
            List<VideoVo> list = new ArrayList<>();
            // 查询二级分类封装进一级分类中
            for (EduVideo two : eduVideosTwo){
                if(oneId.equals(two.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(two, videoVo);
                    list.add(videoVo);
                }
            }
            chapterVo.setChildren(list);
            finalList.add(chapterVo);
        }
        return finalList;
    }

    // 删除章节方法
    @Override
    public boolean deleteChapter(String chapterId) {
        // 判断章节下面是否存在视频
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        List<EduVideo> list = videoService.list(wrapper);
        if (list != null){
            // 章节下存在视频，进行递归删除
            for (EduVideo one : list){
                String id = one.getId();
                boolean flag = videoService.removeById(one);
                log.info(flag ? "Video.id:{} 删除成功" : "Video.id:{} 删除失败,请检查...",id);
            }
        }
        // 最后删除章节
        int result = baseMapper.deleteById(chapterId);
        log.info(result > 0 ? "Chapter.id:{} 删除成功" : "Chapter.id:{} 删除失败,请检查...",chapterId);
        return result > 0;
    }
}

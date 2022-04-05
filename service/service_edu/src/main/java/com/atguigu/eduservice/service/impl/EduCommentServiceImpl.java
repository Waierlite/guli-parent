package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.UcenterMemberCommon;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.vo.CommentFrontInfo;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-24
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Resource
    private UcenterClient ucenterClient;

    @Override
    public Map<String, Object> getCommentList(Page<EduComment> pageParam, String courseId) {
        // 根据课程id查询评论信息
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        // 查询分页数据
        baseMapper.selectPage(pageParam, wrapper);
        List<EduComment> eduComments = pageParam.getRecords();

        // 查询用户信息封装进用户评论
        List<CommentFrontInfo> commentList = new ArrayList<>();
        // eduComments判空
        if (eduComments.size() > 0) {
            for (EduComment one : eduComments) {
                // 根据评论信息中的用户id查询用户相关信息
                String memberId = one.getMemberId();
                // 使用openFeign根据用户id调用ucenter的方法查询用户信息
                UcenterMemberCommon member = ucenterClient.getUserById(memberId);
                // 封装进前端vo实体类
                CommentFrontInfo commentFrontInfo = new CommentFrontInfo();
                BeanUtils.copyProperties(one, commentFrontInfo);
                // member信息判空避免空指针
                if (member != null) {
                    BeanUtils.copyProperties(member, commentFrontInfo);
                }
                // 添加进入list数组
                commentList.add(commentFrontInfo);
            }
        }
        // 返回map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());

        return map;
    }
}

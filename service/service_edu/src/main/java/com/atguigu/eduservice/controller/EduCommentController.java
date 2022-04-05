package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ResultJson;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-24
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Resource
    private EduCommentService commentService;

    // 分页查询评论信息
    @GetMapping("getCommentInfo/{current}/{limit}")
    public ResultJson getCommentInfo(@PathVariable long current, @PathVariable long limit, String courseId) {
        // 创建page对象(使用baomidou的包进行封装page对象)
        Page<EduComment> pageParam = new Page<>(current, limit);
        // 返回分页所有数据
        Map<String, Object> map = commentService.getCommentList(pageParam, courseId);
        return ResultJson.ok().data(map);
    }

    // 增加评论
    @PostMapping("addComment")
    public ResultJson addComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return ResultJson.error().message("请登录后在参与评论。");
        }
        // 设置用户id
        eduComment.setMemberId(memberId);

        // 添加评论信息
        commentService.save(eduComment);
        return ResultJson.ok();
    }

}


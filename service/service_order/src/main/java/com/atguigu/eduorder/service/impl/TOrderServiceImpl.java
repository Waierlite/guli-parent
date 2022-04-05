package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.CourseFrontInfoCommon;
import com.atguigu.commonutils.UcenterMemberCommon;
import com.atguigu.eduorder.client.CourseClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.mapper.TOrderMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-25
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Resource
    private CourseClient courseClient;

    @Resource
    private UcenterClient ucenterClient;

    // 生成订单方法
    @Override
    public String createOrder(String courseId, String memberId) {
        // 通过远程调用获取用户信息
        UcenterMemberCommon user = ucenterClient.getUserById(memberId);
        // 通过远程调用获取课程信息
        CourseFrontInfoCommon courseInfo = courseClient.getCourseInfo(courseId);
        // 创建Order对象，向Order对象里面设置需要数据
        TOrder order = new TOrder();
        // 使用工具类生成订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        // 设置其他值
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(user.getMail());
        order.setNickname(user.getNickname());
        order.setStatus(0);  // 支付状态
        order.setPayType(1); // 支付方式：微信（1）

        int result = baseMapper.insert(order);
        if (!(result > 0)) {
            throw new RuntimeException("生成订单失败，请检查。");
        }
        // 返回订单号
        return order.getOrderNo();
    }
}

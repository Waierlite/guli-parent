package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-25
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberId);
}

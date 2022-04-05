package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-20
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    int countRegister(String date);
}

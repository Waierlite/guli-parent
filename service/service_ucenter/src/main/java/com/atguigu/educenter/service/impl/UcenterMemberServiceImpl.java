package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5Util;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ChenJK
 * @since 2022-03-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    // 登录方法
    @Override
    public String login(LoginVo loginVo) {
        // 获取用户邮箱和密码
        String mail = loginVo.getMail();
        String password = loginVo.getPassword();

        // 邮箱和密码非空判断
        if (StringUtils.isEmpty(mail) || StringUtils.isEmpty(password)){
            throw new RuntimeException("非法用户尝试登录");
        }

        // 邮箱正则
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        if (!matcher.matches()){
            throw new RuntimeException("邮箱格式不正确");
        }

        // 判断邮箱是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mail",mail);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        if (ucenterMember == null){
            throw new RuntimeException("非法用户尝试登录");
        }

        // 判断密码是否正确(先将明文密码进行加密在和数据库中进行比对)
        String md5Password = MD5Util.getMD5(password);
        if (!md5Password.equals(ucenterMember.getPassword())){
            throw new RuntimeException("非法用户尝试登录");
        }

        // 判断用户是否被禁用'
        if (ucenterMember.getIsDisabled()){
            throw new RuntimeException("非法用户尝试登录");
        }

        // 登录成功返回使用jwt生成token返回
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname() ,ucenterMember.getAvatar());
    }

    // 注册方法
    @Override
    public boolean register(RegisterVo registerVo) {
        // 获取数据
        String code = registerVo.getCode();  // 验证码
        String mail = registerVo.getMail();  // 电子邮箱
        String nickname = registerVo.getNickname();  // 昵称
        String password = registerVo.getPassword();  // 密码

        // 非空判断
        if (StringUtils.isEmpty(code)
                || StringUtils.isEmpty(mail)
                || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(password)){
            throw new RuntimeException("注册信息非法");
        }

        // 判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mail);
        if (!code.equals(redisCode)){
            throw new RuntimeException("验证不正确");
        }

        // 判断邮箱是否已经注册
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mail",mail);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new RuntimeException("该用户已经注册，请勿重复注册。");
        }

        // 注册成功
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo,ucenterMember);
        ucenterMember.setPassword(MD5Util.getMD5(password));  // 加密用户密码
        ucenterMember.setIsDisabled(false); // 用户不禁用
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");  // 默认头像
        int insert = baseMapper.insert(ucenterMember);

        return insert > 0;
    }

    // 根据openid查询匹配行
    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    // 统计注册人数
    @Override
    public int countRegister(String date) {
        return baseMapper.countRegister(date);
    }
}

package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.exception.Myexception;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfo1DTO;
import com.example.demo.pojo.vo.UserInfoVo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>implements UserInfoService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public void add(UserInfoVo userInfoVo) {
        //设置id
        userInfoVo.setId(permissionVerification());

       QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>().eq("phone", userInfoVo.getPhone());
        Long l = userInfoMapper.selectCount(queryWrapper);
        if (l > 0) {
            throw new Myexception("该手机号已经被注册",2005);
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoVo, userInfo);

        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper1);
        userInfo.setType(user.getType());
        userInfoMapper.insert(userInfo);

    }

    @Override
    public UserInfo1DTO getUserinfo(Integer id) {
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        UserInfo1DTO userInfo1DTO = userInfoMapper.selectUserInfoByself(id, teacherId);

        return userInfo1DTO;
    }


    public Integer permissionVerification() {
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);

        return user.getId();
    }
}

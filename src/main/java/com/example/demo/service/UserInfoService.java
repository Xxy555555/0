package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfo1DTO;
import com.example.demo.pojo.vo.UserInfoVo;

import java.util.List;

public interface UserInfoService extends IService<UserInfo> {
    void add(UserInfoVo userInfoVo);

    UserInfo1DTO getUserinfo();
}

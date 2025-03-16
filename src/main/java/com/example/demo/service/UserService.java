package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.RegisterUser;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.vo.LoginVo;
import com.example.demo.pojo.vo.PasswordVo;
import com.example.demo.pojo.vo.UserInfoVo;
import com.example.demo.pojo.vo.UserVo;
import com.example.demo.result.ResponseResult;

public interface UserService extends IService<User> {

    LoginVo login(UserVo userVo);

    void sendCaptcha(String emailname);

    void register(RegisterUser registerUser);


    void logout();


    void updatePassword(PasswordVo passwordVo);
}


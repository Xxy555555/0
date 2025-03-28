package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
Page<UserInfo> selectUserByPage(@Param("page") Page<UserInfo> page,@Param("type")Integer type);
    Page<UserInfoDTO> selectUserInfoByPage(@Param("page") Page<UserInfoDTO> page,@Param("type")Integer type);
   List<UserInfo> selectListUserInfo();
}

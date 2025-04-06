package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfo1DTO;
import com.example.demo.pojo.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
Page<UserInfo> selectUserByPage(@Param("page") Page<UserInfo> page,@Param("type")Integer type);
    Page<UserInfo1DTO> selectUserInfoByPage(@Param("page") Page<UserInfoDTO> page, @Param("type")Integer type);
   Page<UserInfo> selectListUserInfo(@Param("page")Page<UserInfo> page,@Param("InternshipStatus") Integer InternshipStatus,@Param("teacherId") Integer teacherId,@Param("name")String name);
    UserInfo1DTO selectUserInfoByself(@Param("id")Integer id, @Param("teacherId")Integer teacherId);
}

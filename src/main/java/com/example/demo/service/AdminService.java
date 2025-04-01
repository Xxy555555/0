package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Positions;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfo1DTO;
import com.example.demo.pojo.dto.UserInfoDTO;
import com.example.demo.pojo.vo.IsDisable;
import com.example.demo.pojo.vo.JobStatus;
import com.example.demo.pojo.vo.PositionMsgVo;
import com.example.demo.pojo.vo.UserInfoVo;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface AdminService {
    void bindStudent(Map<String, Integer> map);

    void deleteUser(Integer id);

    void resetPassword(Integer id);

    void disable(IsDisable isDisable);

    void addUser(User user);

    Page<UserInfo1DTO> getUserInfo(Integer current, Integer Size, Integer type);

    void exportData(HttpServletResponse response);

    void Review(JobStatus jobStatus);

    IPage<Positions> getJobMsg(PositionMsgVo positionMsgVo);

    List<Object> getUserNum();
}

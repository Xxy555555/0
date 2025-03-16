package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Positions;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfoDTO;
import com.example.demo.pojo.vo.IsDisable;
import com.example.demo.pojo.vo.JobStatus;
import com.example.demo.pojo.vo.UserInfoVo;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface AdminService {
    void bindStudent(Map<String, Integer> map);

    void deleteUser(Integer id);

    void resetPassword(Integer id);

    void disable(IsDisable isDisable);

    void addUser(User user);

    List<UserInfoDTO> getUserInfo(Integer current, Integer Size, Integer type);

    void exportData(HttpServletResponse response);

    void Review(JobStatus jobStatus);

    List<Positions> getJobMsg();

}

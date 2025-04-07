package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.*;
import com.example.demo.pojo.dto.Position1DTO;
import com.example.demo.pojo.dto.PositionDTO;
import com.example.demo.pojo.dto.UserInfo1DTO;
import com.example.demo.pojo.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService extends IService<Student> {
    void complete(TaskVo taskVo);

    String oss(MultipartFile file,String fileName);

    void storage(String url);

    IPage<PositionDTO> getPosition(CompanyPageVo companyPageVo);

    void addResume(PublishResume publishResume);

    Page<CompanyAndStudent> getIsPass(MyPage myPage);

    void acceptOffer(AcceptOffer acceptOffer);

    Page<Task> getTask(GetTaskVo getTaskVo);

    Page<Position1DTO> getResume(CompanyPageVo companyPageVo);

    UserInfo1DTO getUserinfo();

}

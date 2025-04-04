package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Company;
import com.example.demo.pojo.CompanyAndStudent;
import com.example.demo.pojo.MyPage;
import com.example.demo.pojo.Student;
import com.example.demo.pojo.dto.PositionDTO;
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
}

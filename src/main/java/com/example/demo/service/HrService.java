package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.*;
import com.example.demo.pojo.dto.GetPositionInfoDTO;
import com.example.demo.pojo.dto.OPinionDTO;
import com.example.demo.pojo.dto.StudentInternshipInfoDTO;
import com.example.demo.pojo.vo.*;

import java.util.List;

public interface HrService {
    void sendOpinion(OpinionVo opinionVo);

    void publishJobPostings(CompanyVo companyVo);

    List<Student> getResume(GetResumeVo getResumeVo);

    void hiredStudent(PositionVo positionVo);

    Page<Positions> getReview(GetReviewVo  getReviewVo);


    void publishReview(JobStatus jobStatus);

    void publishStatus(JobStatus jobStatus);

    String registerCompany(Company1Vo companyVo);

    Company getCompanyMsg();

    Page<StudentInternshipInfoDTO> getOpinion(GetStudentIfoVo getStudentIfoVo);

    Page<UserInfo> getStudentIfo(GetStudentIfoVo getStudentIfoVo);

    Page<GetPositionInfoDTO> getPositionInfo(Position1Vo position1Vo);
}

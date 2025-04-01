package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Company;
import com.example.demo.pojo.MyPage;
import com.example.demo.pojo.Positions;
import com.example.demo.pojo.Student;
import com.example.demo.pojo.dto.OPinionDTO;
import com.example.demo.pojo.dto.StudentInternshipInfoDTO;
import com.example.demo.pojo.vo.*;

import java.util.List;

public interface HrService {
    void sendOpinion(OpinionVo opinionVo);

    void publishJobPostings(CompanyVo companyVo);

    List<Student> getResume(GetResumeVo getResumeVo);

    void hiredStudent(PositionVo positionVo);

    Page<Positions> getReview(MyPage myPage);


    void publishReview(JobStatus jobStatus);

    void publishStatus(JobStatus jobStatus);

    String registerCompany(Company1Vo companyVo);

    Company getCompanyMsg();

    Page<StudentInternshipInfoDTO> getOpinion(GetStudentIfoVo getStudentIfoVo);
}

package com.example.demo.service;

import com.example.demo.pojo.Positions;
import com.example.demo.pojo.Student;
import com.example.demo.pojo.vo.*;

import java.util.List;

public interface HrService {
    void sendOpinion(OpinionVo opinionVo);

    void publishJobPostings(CompanyVo companyVo);

    List<Student> getResume(GetResumeVo getResumeVo);

    void hiredStudent(PositionVo positionVo);

    List<Positions> getReview();


    void publishReview(JobStatus jobStatus);

    void publishStatus(JobStatus jobStatus);

    void registerCompany(String companyName);
}

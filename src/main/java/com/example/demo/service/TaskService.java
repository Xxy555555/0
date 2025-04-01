package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Opinion;
import com.example.demo.pojo.StudentTask;
import com.example.demo.pojo.Task;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.InternshipInfoDTO;
import com.example.demo.pojo.dto.OPinionDTO;
import com.example.demo.pojo.dto.StudentTaskDTO;
import com.example.demo.pojo.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService extends IService<Task> {
    void publishTask(PublishTask publishTask);

    void sendMsg();

    Page<Task> getTask(GetContentVo getContentVo);

    Page<StudentTaskDTO> getContent(GetContentVo getContentVo);

    Page<OPinionDTO> getOpinion(GetContentVo getContentVo);

    void addevaluate(Evaluate evaluate);

    Page<UserInfo> getInfo(InternshipStatusVo internshipStatusVo);

    Page<InternshipInfoDTO> getInternshipInfo(InternshipInfoVo internshipInfoVo);

    void StartInternship(Integer studentId);

    Page<StudentTask> getOneComplete(GetContentVo getContentVo);
}

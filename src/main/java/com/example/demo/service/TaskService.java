package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Opinion;
import com.example.demo.pojo.StudentTask;
import com.example.demo.pojo.Task;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.InternshipInfoDTO;
import com.example.demo.pojo.vo.Evaluate;
import com.example.demo.pojo.vo.GetContentVo;
import com.example.demo.pojo.vo.InternshipStatusVo;
import com.example.demo.pojo.vo.PublishTask;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService extends IService<Task> {
    void publishTask(PublishTask publishTask);

    void sendMsg();

    List<Task> getTask(GetContentVo getContentVo);

    List<StudentTask> getContent(GetContentVo getContentVo);

    List<Opinion> getOpinion(GetContentVo getContentVo);

    void addevaluate(Evaluate evaluate);

    List<UserInfo> getInfo(InternshipStatusVo internshipStatusVo);

    List<InternshipInfoDTO> getInternshipInfo(String name);

    void StartInternship(Integer studentId);

    List<StudentTask> getOneComplete(GetContentVo getContentVo);
}

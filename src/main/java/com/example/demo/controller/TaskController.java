package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.*;
import com.example.demo.pojo.dto.InternshipInfoDTO;
import com.example.demo.pojo.dto.OPinionDTO;
import com.example.demo.pojo.dto.StudentTaskDTO;
import com.example.demo.pojo.vo.*;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.websocket.server.PathParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 发布任务
     * @param publishTask
     * @return
     */
    @PostMapping("/publishTask")
    private ResponseResult publishTask(@RequestBody @Validated PublishTask publishTask) {
        taskService.publishTask(publishTask);
        return ResponseResult.ok("发布成功");
    }


    /**
     *
     * 自动发送消息
     * @return
     */

    //自动调用
    @GetMapping("/sendMsg")
    private ResponseResult sendMsg() {
        taskService.sendMsg();
        return ResponseResult.ok("发送成功");
    }


    //查看 查看学生反馈的状态 hr反馈的状态

    /**
     * 查看任务
     * @param getContentVo
     * @return
     */
    @GetMapping("/getTask")
    private ResponseResult getTask( @Validated GetContentVo getContentVo) {
        Page<Task> Tasks =taskService.getTask(getContentVo);
        return ResponseResult.ok(Tasks);
    }

    /**
     * 查看学生完成任务情况
     * @param getContentVo
     * @return
     */
    @GetMapping("/getContent")
    private ResponseResult getContent( @Validated GetContentVo getContentVo) {
        Page<StudentTaskDTO> studentTasks =taskService.getContent(getContentVo);
        return ResponseResult.ok(studentTasks);
    }

    /**
     * 查看意见
     * @param getContentVo
     * @return
     */
    @GetMapping("/getOpinion")
    private ResponseResult getOpinion( @Validated GetContentVo getContentVo) {
        Page<OPinionDTO> opinions =taskService.getOpinion(getContentVo);
        return ResponseResult.ok( opinions);
    }

    /**
     * 提意见
     * @param evaluate
     * @return
     */
    @PutMapping("/addevaluate")
    private ResponseResult addevaluate(@RequestBody @Validated Evaluate evaluate) {
             taskService.addevaluate(evaluate);
        return ResponseResult.ok( "评价成功");
    }

    /**
     * 获得学生基本信息
     * @param
     * @return
     */
    @GetMapping("/getInfo")
    private ResponseResult getInfo(InternshipStatusVo internshipStatusVo) {
        Page<UserInfo>userInfos=taskService.getInfo(internshipStatusVo);
        return ResponseResult.ok( userInfos);
    }
    //获取学生实习信息
    @GetMapping("/getInternshipInfo")
    private ResponseResult getInternshipInfo( InternshipInfoVo internshipInfoVo) {
        Page<InternshipInfoDTO>userInfos=taskService.getInternshipInfo(internshipInfoVo);
        return ResponseResult.ok( userInfos);
    }
    //实习开始
    @PutMapping("/StartInternship/{studentId}")
    private ResponseResult StartInternship(@PathVariable() Integer studentId) {
        taskService.StartInternship(studentId);
        return ResponseResult.ok( "操作成功");
    }
    //查看某一个学生的任务完成情况
    @GetMapping("/getOneComplete")
    private ResponseResult getOneComplete( @Validated GetContentVo getContentVo) {
        Page<StudentTask> studentTasks =taskService.getOneComplete(getContentVo);
        return ResponseResult.ok(studentTasks);
    }
}

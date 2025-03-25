package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.*;
import com.example.demo.pojo.dto.InternshipInfoDTO;
import com.example.demo.pojo.vo.Evaluate;
import com.example.demo.pojo.vo.GetContentVo;
import com.example.demo.pojo.vo.PublishTask;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.websocket.server.PathParam;
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
    private ResponseResult getTask(@RequestBody @Validated GetContentVo getContentVo) {
        List<Task> Tasks =taskService.getTask(getContentVo);
        return ResponseResult.ok(Tasks);
    }

    /**
     * 查看学生完成任务情况
     * @param getContentVo
     * @return
     */
    @GetMapping("/getContent")
    private ResponseResult getContent(@RequestBody @Validated GetContentVo getContentVo) {
        List<StudentTask> studentTasks =taskService.getContent(getContentVo);
        return ResponseResult.ok(studentTasks);
    }

    /**
     * 查看意见
     * @param getContentVo
     * @return
     */
    @GetMapping("/getOpinion")
    private ResponseResult getOpinion(@RequestBody @Validated GetContentVo getContentVo) {
        List<Opinion> opinions =taskService.getOpinion(getContentVo);


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
     * @param name
     * @return
     */
    @GetMapping("/getInfo")
    private ResponseResult getInfo(String name) {
        List<UserInfo>userInfos=taskService.getInfo(name);
        return ResponseResult.ok( userInfos);
    }
    //获取学生实习信息
    @GetMapping("/getInternshipInfo")
    private ResponseResult getInternshipInfo(@PathParam("name") String name) {
        List<InternshipInfoDTO>userInfos=taskService.getInternshipInfo(name);
        return ResponseResult.ok( userInfos);
    }
}

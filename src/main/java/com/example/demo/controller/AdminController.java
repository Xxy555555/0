package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Positions;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfo1DTO;
import com.example.demo.pojo.dto.UserInfoDTO;
import com.example.demo.pojo.vo.IsDisable;
import com.example.demo.pojo.vo.JobStatus;
import com.example.demo.pojo.vo.PositionMsgVo;
import com.example.demo.pojo.vo.UserInfoVo;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 绑定学生
     *
     * @param map
     * @return
     */
    @PutMapping("/bindStudent")
    private ResponseResult bindStudent(@RequestBody Map<String, Integer> map) {
        adminService.bindStudent(map);

        return ResponseResult.ok("绑定成功");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteUser/{id}")
    private ResponseResult deleteUser(@PathVariable("id") Integer id) {
        adminService.deleteUser(id);

        return ResponseResult.ok("删除成功");
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @PutMapping("/resetPassword/{id}")
    private ResponseResult resetPassword(@PathVariable("id") Integer id) {
        adminService.resetPassword(id);

        return ResponseResult.ok("重置成功密码为：123456");
    }

    /**
     * 账号的启用和禁用
     *
     * @param isDisable
     * @return
     */
    @PutMapping("/disable")
    private ResponseResult disable(@RequestBody @Validated IsDisable isDisable) {
        adminService.disable(isDisable);

        return ResponseResult.ok("操作成功");
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    private ResponseResult addUser(@RequestBody @Validated User user) {
        adminService.addUser(user);

        return ResponseResult.ok("添加成功");
    }

    /**
     * 导出Excel
     * @param response
     */
    @GetMapping(value = "/exportData")
    public void exportData( HttpServletResponse response) {
        adminService.exportData(response);
        //return ResponseResult.ok("导出成功");
    }

    /**
     * 查询用户消息
     * @return
     */
    @GetMapping("/getUserInfo")
    private ResponseResult getUserInfo(@PathParam("current") Integer current, @PathParam("size")Integer size,@PathParam("type") Integer type) {
        Page<UserInfo1DTO> userInfoVo=adminService.getUserInfo(current,size,type);

        return ResponseResult.ok(userInfoVo);
    }
    //审核 招聘信息
    @PutMapping("/Review")
    private ResponseResult Review(@RequestBody JobStatus jobStatus) {
        adminService.Review(jobStatus);

        return ResponseResult.ok("操作成功");
    }
    //查看提交的招聘信息
    @GetMapping("/getJobMsg")
    private ResponseResult getJobMsg( @Validated PositionMsgVo positionMsgVo) {
        IPage<Positions> position=adminService.getJobMsg(positionMsgVo);

        return ResponseResult.ok(position);

    }
    @GetMapping("/getUserNum")
    private ResponseResult getUserNum() {
        List<Object>  UserNum=adminService.getUserNum();

        return ResponseResult.ok(UserNum);

    }
}
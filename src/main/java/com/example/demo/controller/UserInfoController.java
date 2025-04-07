package com.example.demo.controller;

import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.UserInfo1DTO;
import com.example.demo.pojo.vo.UserInfoVo;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.UserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     *添加用户信息
     * @param userInfoVo
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody @Validated UserInfoVo userInfoVo) {
        userInfoService.add(userInfoVo);
       return ResponseResult.ok("添加成功");
    }
    @GetMapping("/getUserinfo/{id}")
    public ResponseResult getUserinfo(@PathVariable("id")Integer id) {
        UserInfo1DTO userInfo= userInfoService.getUserinfo(id);
        return ResponseResult.ok(userInfo);
    }
    @GetMapping("/getUserInfoById/{id}")
    public ResponseResult getUserInfoById(@PathVariable("id")Integer id) {
       UserInfoVo userInfos = userInfoService.getUserInfoById(id);
        return ResponseResult.ok(userInfos);
    }
}

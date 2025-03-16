package com.example.demo.controller;

import com.example.demo.pojo.JavaCache;
import com.example.demo.pojo.RegisterUser;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.vo.LoginVo;
import com.example.demo.pojo.vo.PasswordVo;
import com.example.demo.pojo.vo.UserInfoVo;
import com.example.demo.pojo.vo.UserVo;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTUtil;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
//@ConfigurationProperties
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param
     * @return
     */
    @PostMapping ("/login")
    private ResponseResult login(@RequestBody UserVo userVo)
    {
        LoginVo loginVo = userService.login(userVo);
        return ResponseResult.ok(loginVo);
    }


    @GetMapping("/sendCaptcha/{emailname}")
    private  ResponseResult sendCaptcha( @PathVariable("emailname")String emailname) {
         userService.sendCaptcha(emailname);

        System.out.println(JavaCache.get("code"));
        return ResponseResult.ok("发送成功");
    }

    @PostMapping ("/register")
    private ResponseResult register(@RequestBody RegisterUser registerUser)
    {
        userService.register(registerUser);

        return ResponseResult.ok("注册成功");
    }


    @PostMapping ("/logout")
    private ResponseResult logout()
    {
        userService.logout();

        return ResponseResult.ok("退出成功");
    }

    @PutMapping ("/updatePassword")
    private ResponseResult updatePassword(@RequestBody@Validated PasswordVo passwordVo)
    {
        userService.updatePassword(passwordVo);

        return ResponseResult.ok("修改成功");
    }
}

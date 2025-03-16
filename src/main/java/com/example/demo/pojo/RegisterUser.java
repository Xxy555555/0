package com.example.demo.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,16}$",message = "请输入3-16位的用户名")
    private String userName;//用户名

    @NotNull
    private String password;//密码

    @NotNull
    private String confirmPassword;//确认密码


    @NotNull
    @Email
    private String email;//邮箱

    @NotNull
    private String captcha;//验证码

    private Integer type;   //1 学生 2老师 3HR

}

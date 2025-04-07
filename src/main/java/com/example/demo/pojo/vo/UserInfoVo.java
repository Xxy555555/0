package com.example.demo.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    @ExcelProperty("id")

    private Integer id;//主键ID
    @ExcelProperty("姓名")
    @NotNull(message = "姓名不能为空")
    @Pattern(regexp = "^\\S{1,10}$",message = "姓名长度为1-10")
    private String name;//姓名
    @ExcelProperty("手机号")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "请输入合法的手机号")
    private String phone; //手机号
    @ExcelProperty("身份证号")
    @NotNull(message = "身份证号不能为空")
    @Pattern(regexp = "^(^\\d{17})([0-9]|X)$",message = "请输入合法的身份号")
    private String idCard;//身份证号
    @ExcelProperty("年龄")
    @NotNull(message = "年龄不能为空")
    private Integer age;//年龄
    @ExcelProperty("性别")
    @NotNull(message = "性别不能为空")
    private Integer gender;//性别
    @ExcelProperty("身高")
    @NotNull(message = "身高不能为空")
    private Double height;//身高
    @ExcelProperty("体重")
    @NotNull(message = "体重不能为空")
    private Double weight;//体重

    
    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}

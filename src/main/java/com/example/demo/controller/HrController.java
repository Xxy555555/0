package com.example.demo.controller;

import com.example.demo.pojo.Positions;
import com.example.demo.pojo.Student;
import com.example.demo.pojo.vo.*;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/Hr")
public class HrController {
    @Autowired
   private HrService hrService;

    /**
     * 发表意见
     * @param opinionVo
     * @return
     */
    @PostMapping("/sendOpinion")
    private ResponseResult sendOpinion(@RequestBody @Validated OpinionVo opinionVo) {

        hrService.sendOpinion(opinionVo);
        return ResponseResult.ok("发送成功");
    }

    /**
     * 创建招聘信息
     * @param companyVo
     * @return
     */
    @PostMapping("/publishJobPostings")
    private ResponseResult publishJobPostings(@RequestBody @Validated CompanyVo companyVo) {

        hrService.publishJobPostings(companyVo);
        return ResponseResult.ok("创建成功");
    }
    //发布招聘信息 录用学生 返回学生信息到老师端

    /**
     * 查看简历
     * @param getResumeVo
     * @return
     */
    @GetMapping("/getResume")
    private ResponseResult getResume(@Validated GetResumeVo getResumeVo) {

       List< Student> students=hrService.getResume(getResumeVo);
        return ResponseResult.ok(students);
    }
    //录用学生
    @PutMapping("/hiredStudent")
    private ResponseResult hiredStudent(@RequestBody PositionVo positionVo) {

        hrService.hiredStudent(positionVo);
        return ResponseResult.ok("操作成功");
    }
    //查看招聘信息状态
    @GetMapping("/getReview")
    private ResponseResult getReview() {

        List<Positions>info=hrService.getReview();
        return ResponseResult.ok(info);
    }
    //提交审核
    @PutMapping("/publishReview")
    private ResponseResult publishReview(@RequestBody JobStatus jobStatus) {

        hrService.publishReview(jobStatus);
        return ResponseResult.ok("操作成功");
    }
    //招聘 不招娉
    @PutMapping("/publishStatus")
    private ResponseResult publishStatus(@RequestBody JobStatus jobStatus) {

        hrService.publishStatus(jobStatus);
        return ResponseResult.ok("操作成功");
    }
    //注册公司
    @PostMapping("/registerCompany")
    private ResponseResult registerCompany(@RequestParam  String companyName) {

        hrService.registerCompany(companyName);
        return ResponseResult.ok("注册成功");
    }
}

package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Company;
import com.example.demo.pojo.CompanyAndStudent;
import com.example.demo.pojo.MyPage;
import com.example.demo.pojo.dto.PositionDTO;
import com.example.demo.pojo.vo.*;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.StudentService;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentContorller {
    @Autowired
    private StudentService studentService;

    /**
     *
     * 完成任务
     * @param taskVo
     * @return
     */
    //
    @PostMapping("/complete")
    private ResponseResult complete(@RequestBody @Validated TaskVo taskVo) {

        studentService.complete(taskVo);
        return ResponseResult.ok("完成任务");
    }


    /**
     * oss
     * @param file
     * @param fileName
     * @return
     */
    @PostMapping("/oss")
    private ResponseResult oss(@RequestBody @Validated  MultipartFile file,String fileName) {


        return ResponseResult.ok(studentService.oss( file,fileName));
    }

    /**
     * 简历入库
     * @param
     * @return
     */
    @PostMapping("/storage")
    private ResponseResult storage(@Param("url") String url) {
        studentService.storage(url);

        return ResponseResult.ok("上传成功");
    }
    //查看招聘信息 反馈每周状态给老师 投递简历到hr

    /**
     * 查看招聘信息
     * @param companyPageVo
     * @return
     */
    @GetMapping("/getPosition")//
    private ResponseResult getPosition(CompanyPageVo companyPageVo  ) {
        IPage<PositionDTO> positionList  =studentService.getPosition(companyPageVo);

        return ResponseResult.ok(positionList);
    }

    /**
     * 提交简历
     * @param publishResume
     * @return
     */
    @PostMapping("/addResume")//
    private ResponseResult addResume(@RequestBody @Validated PublishResume publishResume  ) {
        studentService.addResume(publishResume);

        return ResponseResult.ok("提交成功");
    }
//接受offer
    @GetMapping("/getIsPass")//
    private ResponseResult getIsPass(MyPage myPage) {
        Page<CompanyAndStudent> companyList  =studentService.getIsPass(myPage);

        return ResponseResult.ok(companyList);
    }

    @PutMapping("/acceptOffer")//
    private ResponseResult acceptOffer(@RequestBody @Validated AcceptOffer acceptOffer) {
        studentService.acceptOffer(acceptOffer);

        return ResponseResult.ok("操作成功");
    }
}

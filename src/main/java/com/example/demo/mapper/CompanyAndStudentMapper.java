package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.CompanyAndStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyAndStudentMapper extends BaseMapper<CompanyAndStudent> {
    void selectByIsPass(@Param("studentId")Integer studentId);
}

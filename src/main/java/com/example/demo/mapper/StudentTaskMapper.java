package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.StudentTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentTaskMapper extends BaseMapper<StudentTask> {
}

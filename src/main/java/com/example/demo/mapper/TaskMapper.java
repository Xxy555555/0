package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    void  insertTask(Task task);
    Page<Task> selectPageByTitle(@Param("page")Page<Task> page,@Param("title") String title,@Param("teacherId") Integer teacherId);
}

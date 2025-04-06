package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.StudentTask;
import com.example.demo.pojo.Task;
import com.example.demo.pojo.dto.StudentTaskDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    void  insertTask(Task task);
    Page<Task> selectPageByTitle(@Param("page")Page<Task> page,@Param("title") String title,@Param("teacherId") Integer teacherId);
    Page<StudentTaskDTO> selectOneByTaskId(@Param("page")Page<StudentTaskDTO> page,
                                           @Param("taskId") Integer taskId,
                                           @Param("teacherId") Integer teacherId,
                                           @Param("studentId")Integer studentId,
                                           @Param("name")String name);

    Page<Task> selectPageByid(@Param("page")Page<Task> page,@Param("taskId")Integer taskId,@Param("title")String title );


}

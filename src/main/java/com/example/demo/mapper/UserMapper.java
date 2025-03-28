package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.User;
import com.example.demo.pojo.dto.InternshipInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<InternshipInfoDTO> selectInternshipInfoDTO(@Param("name") String name, @Param("teacherId") Integer teacherId);
}

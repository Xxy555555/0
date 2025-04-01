package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.User;
import com.example.demo.pojo.dto.InternshipInfoDTO;
import com.example.demo.pojo.dto.TotalNumberDTO;
import com.example.demo.pojo.dto.UserNumDTO;
import com.example.demo.pojo.dto.reviewStatusNumDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Page<InternshipInfoDTO> selectInternshipInfoDTO(@Param("page") Page<InternshipInfoDTO> page ,@Param("name") String name, @Param("teacherId") Integer teacherId);
    List<UserNumDTO> selectUserNumDTO();
    List<TotalNumberDTO> selectPositionNum();
    List<reviewStatusNumDTO> selectreviewStatusNum();
}

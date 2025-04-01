package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.MyPage;
import com.example.demo.pojo.Opinion;
import com.example.demo.pojo.dto.OPinionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpinionMapper extends BaseMapper<Opinion> {
    Page<OPinionDTO> getOpinion(@Param("page")Page<OPinionDTO> page, @Param("teacherId")Integer teacherId, @Param("studentId")Integer studentId,@Param("name")String name);
}

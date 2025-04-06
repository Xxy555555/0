package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Positions;
import com.example.demo.pojo.dto.Position1DTO;
import com.example.demo.pojo.dto.PositionDTO;
import com.example.demo.pojo.vo.JobStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PositionMapper extends BaseMapper<Positions> {
     IPage<Positions> selectPageByPosition(@Param("page") Page<Positions> page, @Param("positionName")String postionName, @Param("reviewStatus") List<Integer> reviewStatus);

    Page<Position1DTO> getResume(@Param("page") Page<Position1DTO> page, @Param("position") String position, @Param("studentId") Integer studentId);
}

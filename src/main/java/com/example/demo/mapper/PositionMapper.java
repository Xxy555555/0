package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Positions;
import com.example.demo.pojo.vo.JobStatus;
import org.apache.ibatis.annotations.Param;


public interface PositionMapper extends BaseMapper<Positions> {
     IPage<Positions> selectPageByPosition(@Param("page") Page<Positions> page, @Param("positionName")String postionName, @Param("reviewStatus") Integer reviewStatus);

}

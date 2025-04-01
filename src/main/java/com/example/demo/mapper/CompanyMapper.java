package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Company;
import com.example.demo.pojo.dto.PositionDTO;
import com.example.demo.pojo.dto.StudentInternshipInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
IPage<PositionDTO> selectPageByLike(@Param("page") Page<PositionDTO> page,
                                    @Param("address") String address,
                                    @Param("companyName") String companyName,
                                    @Param("position") String position,
                                    @Param("num") Integer num,
                                    @Param("salary") Double salary);
Page<StudentInternshipInfoDTO> selectStudentInternshipInfo(@Param("page")Page<StudentInternshipInfoDTO>page,@Param("name")String name,@Param("HrId")Integer hrId);
}

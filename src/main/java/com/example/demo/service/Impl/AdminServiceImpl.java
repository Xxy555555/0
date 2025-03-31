package com.example.demo.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.enums.MapperEnum;
import com.example.demo.enums.MyExcptionEnum;
import com.example.demo.enums.StatusEnum;
import com.example.demo.exception.Myexception;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Positions;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.dto.*;
import com.example.demo.pojo.vo.IsDisable;
import com.example.demo.pojo.vo.JobStatus;
import com.example.demo.pojo.vo.PositionMsgVo;
import com.example.demo.pojo.vo.UserInfoVo;
import com.example.demo.service.AdminService;
import com.example.demo.util.Md5Util;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Override
    @Transactional
    public void bindStudent(Map<String, Integer> map) {
        Integer studentId = map.get("studentId");
        Integer bindId = map.get("bindId");
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<User>().eq("id", studentId);
        User student = userMapper.selectOne(queryWrapper2);
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<User>().eq("id", bindId);
        User binduser = userMapper.selectOne(queryWrapper1);
        if (binduser == null||student == null) {
            throw new Myexception("操作用户不存在",2400);
        }
        if(student .getType()!=1){
            throw new Myexception("被绑定的必须是学生",2400);
        }
        if(binduser .getType()==1||binduser.getType()==0){
            throw new Myexception("绑定者必须是老师或HR",2400);
        }
        if(permissionVerification()==0)
        {

            if (binduser .getType() == 2) {//老师添加
                QueryWrapper<User> queryWrapper=new QueryWrapper<User>().eq("id",studentId);
                User user = userMapper.selectOne(queryWrapper);
                if(user.getType()!=1)
                {
                    throw new Myexception("该用户不是学生无法添加",2400);
                }
                user.setTeacherId(bindId );
                userMapper.updateById(user);
            }else if (binduser .getType() == 3) {//hr绑定
                QueryWrapper<User> queryWrapper=new QueryWrapper<User>().eq("id",studentId);
                User user = userMapper.selectOne(queryWrapper);
                if(user.getType()!=1)
                {
                    throw new Myexception("该用户不是学生无法添加",2400);
                }
                user.setHrId(bindId);
                userMapper.updateById(user);
            }
        }else {
            throw new Myexception("你不是管理员没有该权限",2400);
        }



    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        if(permissionVerification()!=0)
        {
            throw new Myexception("你不是管理员没有该权限",2400);
        }
        userMapper.deleteById(id);
        userInfoMapper.deleteById(id);
    }

    @Override
    public void resetPassword(Integer id) {
        if(permissionVerification()!=0)
        {
            throw new Myexception("你不是管理员没有该权限",2400);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null)
        {
            throw new Myexception("该用户不存在",24001);
        }
        user.setPassword(Md5Util.getMD5String("123456"));
        userMapper.updateById(user);
    }

    @Override
    @Transactional
    public void disable(IsDisable isDisable) {
        Integer id = isDisable.getId();
        Integer disable = isDisable.getDisable();
        if (permissionVerification() != 0) {
            throw new Myexception("你不是管理员没有该权限", 2400);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new Myexception("该用户不存在", 24001);
        }

            user.setDisable(disable);
            userMapper.updateById(user);


    }

    @Override
    public void addUser( User user) {
        if(permissionVerification()!=0)
        {
            throw new Myexception("你不是管理员没有该权限",2400);
        }
        user.setPassword(Md5Util.getMD5String("123456"));
        userMapper.insert(user);
    }

    @Override
    public List<UserInfo1DTO> getUserInfo(Integer current, Integer size, Integer type) {
        if(permissionVerification()!=0)
        {
            throw new Myexception("你不是管理员没有该权限",2400);
        }
        Page<UserInfoDTO> objectPage = new Page<>(current,size);
        if(type==0){//查询所有用户

            Page<UserInfo1DTO> userInfoDTOPage = userInfoMapper.selectUserInfoByPage(objectPage,null);

            return userInfoDTOPage.getRecords();
        } else  {


            Page<UserInfo1DTO> userInfoDTOPage = userInfoMapper.selectUserInfoByPage(objectPage,type);

            return userInfoDTOPage.getRecords();

        }



    }

    @Override
    public void exportData(HttpServletResponse response)  {//Excel
//        if(permissionVerification()!=0)
//        {
//            throw new Myexception("你不是管理员没有该权限",2400);
//        }
        try {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("用户数据", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            List<UserInfo> userInfos = userInfoMapper.selectList(null);
            //掩码处理
           for (UserInfo userInfo : userInfos) {
               String prefix = userInfo.getIdCard().substring(0, 4);
               String suffix = userInfo.getIdCard().substring(userInfo.getIdCard().length() - 4);
               userInfo.setIdCard( prefix + "****" + suffix);
           }

        // 使用 EasyExcel 导出
            EasyExcel.write(response.getOutputStream(), UserInfo.class)
                    .sheet("用户信息")
                    .doWrite(userInfos);
        } catch (IOException e) {
            throw new Myexception(e.getMessage(),599);
        }



    }

    @Override
    public void Review(JobStatus jobStatus) {
        if(permissionVerification()!=0)
        {
            throw new Myexception("你不是管理员没有该权限",2400);
        }
        LambdaQueryWrapper<Positions> ex=new LambdaQueryWrapper<Positions>().eq(Positions::getId,jobStatus.getPositionId());
        Positions positions = positionMapper.selectOne(ex);
        if(positions==null)
        {
            throw new Myexception("无该招聘信息",2400);
        }
        if(jobStatus.getReviewStatus()==2)//通过
        {
            positions.setReviewStatus(2);
            positionMapper.updateById(positions);
        }else  if(jobStatus.getReviewStatus()==3)
        {
            if(jobStatus.getReason()==null)
            {
                throw new Myexception("请填写理由",2400);
            }
            positions.setReviewStatus(3);
            positions.setReason(jobStatus.getReason());
            positionMapper.updateById(positions);
        }
    }

    @Override
    public List<Positions> getJobMsg(PositionMsgVo positionMsgVo) {
        if(permissionVerification()!=0)
        {
            throw new Myexception("你不是管理员没有该权限",2400);
        }
        Page<Positions> positionsPage = new Page<>(positionMsgVo.getCurrent(),positionMsgVo.getSize());

        IPage<Positions> positionsIPage = positionMapper.selectPageByPosition(positionsPage, positionMsgVo.getPosition(),positionMsgVo.getReviewStatus());

        return positionsIPage.getRecords();
    }

    @Override
    public List<Object> getUserNum() {
        List<Object> list=new ArrayList();
        List<UserNumDTO> userNumDTOS = userMapper.selectUserNumDTO();
        List<Map<String,Integer>> collect = userNumDTOS.stream().map(dto -> {
                    MapperEnum mapper = MapperEnum.fromCode(dto.getType());
                    return Map.of(mapper.getPosition(), dto.getUserNum());
                })
                .collect(Collectors.toList());

        List<TotalNumberDTO> integers = userMapper.selectPositionNum();
        List<reviewStatusNumDTO> reviewStatusNumDTOS = userMapper.selectreviewStatusNum();
        List<Map<String,Integer>> collect1 = reviewStatusNumDTOS.stream().map(dto -> {
                    StatusEnum mapper = StatusEnum.fromCode(dto.getReviewStatus());
                    return Map.of(mapper.getStatus(), dto.getNum());
                })
                .collect(Collectors.toList());
        list.addAll(collect);
        list.addAll(integers);
        list.addAll(collect1);
        System.out.println(list);
        return list;
    }


    /**
     * 权限校验
     */
    public Integer permissionVerification() {
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        return user.getType();
    }

}


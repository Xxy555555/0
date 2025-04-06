package com.example.demo.service.Impl;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.exception.Myexception;
import com.example.demo.mapper.*;
import com.example.demo.pojo.*;
import com.example.demo.pojo.dto.PositionDTO;
import com.example.demo.pojo.vo.*;
import com.example.demo.service.StudentService;
import com.example.demo.util.ThreadLocalUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//oss
import java.io.*;
import java.util.UUID;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper  userMapper;
    @Autowired
    private StudentTaskMapper  studentTaskMapper;
    @Autowired
    private TaskMapper  taskMapper;
    @Autowired
    private Oss  oss;
    @Autowired
    private CompanyAndStudentMapper companyAndStudentMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Override
    @Transactional
    public void complete(TaskVo taskVo) {
        if(permissionVerification()!=1)
        {
            throw new Myexception("您不是学生无法进行该操作",119);
        }
        QueryWrapper<Task> Wrapper = new QueryWrapper<Task>().eq("id",taskVo.getTaskId());
        Task task = taskMapper.selectOne(Wrapper);
        if(task==null)
        {

            throw new Myexception("该任务未发布",119);
        }
       if( task.getStartTime().isAfter(LocalDateTime.now()))
       {
           throw new Myexception("该任务还未开始",119);
       }
        if( task.getDeadline().isBefore(LocalDateTime.now()))
        {
            throw new Myexception("该任务已经结束",119);
        }

        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        LambdaQueryWrapper<StudentTask> example = new LambdaQueryWrapper<StudentTask>().eq(StudentTask::getTaskId, taskVo.getTaskId()).eq(StudentTask::getStudentId, id);
        StudentTask studentTask = studentTaskMapper.selectOne(example);

        if(!task.getTeacherId().equals(user.getTeacherId()))
        {
            throw new Myexception("该任务不是您的老师发布的",119);
        }

        studentTask.setContent(taskVo.getContent());
        studentTask.setCompleteStatus(1);

        studentTaskMapper.updateById(studentTask);

    }

    @Override
    @Transactional
    public String oss(MultipartFile file,String fileName) {//oss
        if(file.isEmpty())
        {
            throw new Myexception("请上传文件",119);
        }
        if(permissionVerification()!=1)
        {
            throw new Myexception("您不是学生无法提交简历",119);
        }

        String originalFilename = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();


            //获取拓展名
            String tzName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //随机ID
            UUID uuid = UUID.randomUUID();
            String filename = uuid.toString();

            OSS ossClient = new OSSClientBuilder().build(oss.getEndpoint(), oss.getAccessKeyId(), oss.getAccessKeySecret());


            // 上传文件
            String objectName = fileName+"/"+filename + "." + tzName;
            ossClient.putObject(oss.getBucketName(), objectName, inputStream);

            String url = "https://" + oss.getBucketName() + "." + oss.getEndpoint().split("//")[1] + "/" + objectName;
            ossClient.shutdown();
            return url;
        } catch (Exception e) {

             return e.getMessage();
        }

    }
     //简历入库
    @Override
    @Transactional
    public void storage(String url) {
        if(permissionVerification()!=1)
        {
            throw new Myexception("您不是学生无法进行该操作",9000);
        }
        if(url==null)
        {
            throw new Myexception("请传入url",119);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);//拿到学生user信息

//        CompanyAndStudent companyAndStudent = new CompanyAndStudent();
//        companyAndStudent.setHrId(publishResume.getHrId());
//        companyAndStudent.setStudentId(id);
//        companyAndStudentMapper.insert(companyAndStudent);//添加Hr和学生应聘关系

        Student stu = new Student();
        stu.setStudentId(id);
        stu.setName(user.getUserName());
        stu.setResume(url);
        studentMapper.insert(stu);//简历入库
    }

    @Override
    public IPage<PositionDTO>  getPosition(CompanyPageVo companyPageVo) {
        if(permissionVerification()!=1)
        {
            throw new Myexception("您不是学生无法进行该操作",9000);
        }
        Page<PositionDTO>page = new Page<>(companyPageVo.getCurrent(), companyPageVo.getSize());
        IPage<PositionDTO> companyIPage = companyMapper.selectPageByLike(page,
                                                                     companyPageVo.getAddress(),
                                                                     companyPageVo.getCompanyName(),
                                                                     companyPageVo.getPosition(),
                                                                     companyPageVo.getNum(),
                                                                     companyPageVo.getSalary());
        if(companyIPage.getRecords().isEmpty())
        {
            return null;
        }


        return companyIPage;
    }

    @Override
    public void addResume(PublishResume publishResume) {
        if(permissionVerification()!=1)
        {
            throw new Myexception("您不是学生无法进行该操作",9000);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer studentId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<Positions> example = new LambdaQueryWrapper<Positions>().eq(Positions::getId, publishResume.getPositionId());
        Positions positions = positionMapper.selectOne(example);
        LambdaQueryWrapper<Positions> example1 = new LambdaQueryWrapper<Positions>().eq(Positions::getId, publishResume.getPositionId());
        if(positions==null)
        {
            throw new Myexception("没有该岗位",9000);
        }


        CompanyAndStudent companyAndStudent = new CompanyAndStudent();
        companyAndStudent.setStudentId(studentId);
        companyAndStudent.setPositionId(publishResume.getPositionId());
        companyAndStudentMapper.insert(companyAndStudent);
    }

    @Override
    public Page<CompanyAndStudent> getIsPass(MyPage myPage) {
        if(permissionVerification()!=1)
        {
            throw new Myexception("您不是学生无法进行该操作",9000);
        }

        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer studentId = (Integer) stringObjectMap.get("id");
        Page<CompanyAndStudent>page = new Page<>(myPage.getCurrent(), myPage.getSize());
        LambdaQueryWrapper<CompanyAndStudent> example = new LambdaQueryWrapper<CompanyAndStudent>().eq(CompanyAndStudent::getStudentId, studentId);



        return companyAndStudentMapper.selectPage(page,example);
    }

    @Override
    @Transactional
    public void acceptOffer(AcceptOffer acceptOffer) {
        if(permissionVerification()!=1)
        {
            throw new Myexception("您不是学生无法进行该操作",9000);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer studentId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<CompanyAndStudent> example = new LambdaQueryWrapper<CompanyAndStudent>().eq(CompanyAndStudent::getStudentId, studentId)
                                                                                                    .eq(CompanyAndStudent::getPositionId, acceptOffer.getPositionId());
        CompanyAndStudent companyAndStudent = companyAndStudentMapper.selectOne(example);
        if(companyAndStudent==null)
        {
            throw new Myexception("无该信息",9000);
        }
        if(!companyAndStudent.getIsPass().equals(1))
        {
            throw new Myexception("您的简历没有通过",9000);
        }
        companyAndStudent.setIsAccept(acceptOffer.getIsAccept());
        companyAndStudentMapper.updateById(companyAndStudent);
        if(acceptOffer.getIsAccept()==1)
        {
            LambdaQueryWrapper<UserInfo> ex=new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId,studentId);
            UserInfo userInfo=userInfoMapper.selectOne(ex);
            userInfo.setInternshipStatus(1);
            userInfoMapper.updateById(userInfo);
            LambdaQueryWrapper<Positions> ex1=new LambdaQueryWrapper<Positions>().eq(Positions::getId, acceptOffer.getPositionId());
            Positions positions=positionMapper.selectOne(ex1);
            LambdaQueryWrapper<Company> ex2=new LambdaQueryWrapper<Company>().eq(Company::getId,positions.getCompanyId());
            Company company=companyMapper.selectOne(ex2);
            LambdaQueryWrapper<User> ex3=new LambdaQueryWrapper<User>().eq(User::getId,studentId);
            User user=userMapper.selectOne(ex3);
            user.setHrId(company.getHrId());
            userMapper.updateById(user);
        }
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

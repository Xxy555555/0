package com.example.demo.service.Impl;

import cn.hutool.extra.template.TemplateEngine;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.enums.MyExcptionEnum;
import com.example.demo.exception.Myexception;
import com.example.demo.mapper.*;
import com.example.demo.pojo.*;
import com.example.demo.pojo.dto.GetPositionInfoDTO;
import com.example.demo.pojo.dto.StudentInternshipInfoDTO;
import com.example.demo.pojo.vo.*;
import com.example.demo.service.HrService;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HrServiceImpl implements HrService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OpinionMapper opinionMapper;
    @Autowired//发送邮件
    private JavaMailSender mailSender;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CompanyAndStudentMapper companyAndStudentMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Resource
    private PositionMapper positionMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendOpinion(OpinionVo opinionVo) {

        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR无法提出意见",120);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", opinionVo.getStudentId());
        User user = userMapper.selectOne(queryWrapper);//学生
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<User>().eq("id",id);
        User Hr = userMapper.selectOne(queryWrapper2);//HR
        if(user==null)
        {
            throw new Myexception("没有该学生",2333);
        }
        if(user.getHrId().equals(id))
        {
            Opinion opinion = new Opinion();
            BeanUtils.copyProperties(opinionVo,opinion);
            opinion.setHrId(id);
            opinionMapper.insert(opinion);
            //发邮件
            QueryWrapper<User> queryWrapper1 = new QueryWrapper<User>().eq("id", user.getTeacherId());
            User teacher = userMapper.selectOne(queryWrapper1);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("1774012815@qq.com");//雇主邮箱
            message.setTo(teacher.getEmail());
            message.setSubject("意见信");
            message.setText("有新的雇主信息录入到程序");
            if(message.getText()==null) {

                throw new Myexception("发送失败",2333);
            }
//            mailSender.send(message);
        }else{
            throw new Myexception("您没有招聘该学生",120);
        }

    }

    @Override
    public void publishJobPostings(CompanyVo companyVo) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR无法发布招聘信息",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer HrId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<Company>example = new LambdaQueryWrapper<Company>().eq(Company::getHrId, HrId);
        Company company = companyMapper.selectOne(example);
        if(company==null)
        {
            throw new Myexception("请先注册公司",2333);
        }

        Positions positions = new Positions();
        BeanUtils.copyProperties(companyVo,positions);
        positions.setCompanyId(company.getId());
        positionMapper.insert(positions);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Student> getResume(GetResumeVo getResumeVo) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR无法查看简历",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");
        Page<CompanyAndStudent> page = new Page<>(getResumeVo.getCurrent(),getResumeVo.getSize());
        List<Student> students = new ArrayList<>();
        if(getResumeVo.getStudentId()==null)
        {

            QueryWrapper<CompanyAndStudent> queryWrapper = new QueryWrapper<CompanyAndStudent>().eq("hr_id", hrId);

            Page<CompanyAndStudent> companyAndStudentPage = companyAndStudentMapper.selectPage(page, queryWrapper);
            List<CompanyAndStudent> records = companyAndStudentPage.getRecords();

            for(CompanyAndStudent record:records)
            {

                QueryWrapper<Student> query = new QueryWrapper<Student>().eq("student_id",record.getStudentId());
                Student student = studentMapper.selectOne(query);
                if(student!=null)
                {
                    students.add(student);
                }


            }
            return students;
        }else {
            QueryWrapper<CompanyAndStudent> queryWrapper = new QueryWrapper<CompanyAndStudent>().eq("hr_id", hrId).eq("student_id", getResumeVo.getStudentId());

            CompanyAndStudent companyAndStudent = companyAndStudentMapper.selectOne(queryWrapper);

            QueryWrapper<Student> query = new QueryWrapper<Student>().eq("student_id",companyAndStudent.getStudentId());
            Student student = studentMapper.selectOne(query);
            students.add(student);
            return students;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void hiredStudent(PositionVo positionVo) {//postion
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不录用学生",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");
        //修改Company_and_student库
        LambdaQueryWrapper<CompanyAndStudent>lambdaQueryWrapper=new LambdaQueryWrapper<CompanyAndStudent>().eq(CompanyAndStudent::getPositionId,positionVo.getPositionId())
                                                                                                          .eq(CompanyAndStudent::getStudentId, positionVo.getStudentId());
        CompanyAndStudent companyAndStudent = companyAndStudentMapper.selectOne(lambdaQueryWrapper);
        companyAndStudent.setIsPass(1);
        companyAndStudentMapper.updateById(companyAndStudent);

    }

    @Override
    public Page<Positions> getReview(GetReviewVo getReviewVo) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不能查看",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<Company>example = new LambdaQueryWrapper<Company>().eq(Company::getHrId, hrId);
        Company company = companyMapper.selectOne(example);
        if(company==null)
        {
            throw new Myexception("请先注册公司",2333);
        }
        Page<Positions> page=new Page<>(getReviewVo.getCurrent(),getReviewVo.getSize());
        LambdaQueryWrapper<Positions>ex=new LambdaQueryWrapper<Positions>().eq(Positions::getCompanyId,company.getId()).like(StringUtils.isNotBlank(getReviewVo.getPosition()),
                Positions::getPosition, getReviewVo.getPosition());
        Page<Positions> positions = positionMapper.selectPage(page,ex);
        return positions;
    }

    @Override
    public void publishReview(JobStatus jobStatus) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR提交",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<Company>example = new LambdaQueryWrapper<Company>().eq(Company::getHrId, hrId);
        Company company = companyMapper.selectOne(example);
        LambdaQueryWrapper<Positions>example1 = new LambdaQueryWrapper<Positions>().eq(Positions::getId,jobStatus.getPositionId());
        Positions positions = positionMapper.selectOne(example1);
        if(!company.getId().equals(positions.getCompanyId()))
        {
            throw new Myexception("您没有创建该招聘信息",2333);
        }
        if(positions.getReviewStatus().equals(1))
        {
            throw new Myexception("已经提交过了请不要重复提交",2333);
        }
        positions.setReviewStatus(1);//提交状态
        positionMapper.updateById(positions);
    }

    @Override
    public void publishStatus(JobStatus jobStatus) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不能修改",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<Company>example = new LambdaQueryWrapper<Company>().eq(Company::getHrId, hrId);
        Company company = companyMapper.selectOne(example);
        LambdaQueryWrapper<Positions>example1 = new LambdaQueryWrapper<Positions>().eq(Positions::getId,jobStatus.getPositionId());
        Positions positions = positionMapper.selectOne(example1);
        if(!company.getId().equals(positions.getCompanyId()))
        {
            throw new Myexception("您没有创建该招聘信息",2333);
        }
        if(positions.getReviewStatus().equals(3))
        {
            throw new Myexception("审核未通过",2333);
        }
        if(positions.getReviewStatus().equals(1))
        {
            throw new Myexception("审核中...",2333);
        }
        positions.setPublishStatus(jobStatus.getPublishStatus());
        positionMapper.updateById(positions);
    }

    @Override
    @Transactional
    public String registerCompany(Company1Vo companyVo) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不能修改",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");

        Company company = new Company();
        BeanUtils.copyProperties(companyVo,company);
        company.setHrId(hrId);
        if(companyVo.getId()==null)
        {
            LambdaQueryWrapper<Company>example1 = new LambdaQueryWrapper<Company>().eq(Company::getCompanyEmail,companyVo.getCompanyEmail()).or().eq(Company::getCompanyPhone,companyVo.getCompanyPhone());
            Company company1 = companyMapper.selectOne(example1);
            if(company1!=null)
            {
                throw new Myexception("邮箱或电话已经被注册",2333);
            }
            companyMapper.insert(company);
            return "注册成功";
        }else {

            companyMapper.updateById(company);
            return "修改成功";
        }
    }

    @Override
    public Company getCompanyMsg() {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不能查看",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<Company>example = new LambdaQueryWrapper<Company>().eq(Company::getHrId, hrId);
        Company company = companyMapper.selectOne(example);
        if(company==null)
        {
            throw new Myexception("请您先注册公司",2333);
        }
        return company;
    }

    @Override
    public Page<StudentInternshipInfoDTO> getOpinion(GetStudentIfoVo getStudentIfoVo) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不能查看",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer hrId = (Integer) stringObjectMap.get("id");
        Page<StudentInternshipInfoDTO> page=new Page<>(getStudentIfoVo.getCurrent(),getStudentIfoVo.getSize());
        Page<StudentInternshipInfoDTO> studentInternshipInfoDTOPage = companyMapper.selectStudentInternshipInfo(page, getStudentIfoVo.getStudentName(), hrId,getStudentIfoVo.getStudentId());
        return studentInternshipInfoDTOPage;
    }

    @Override
    public Page<UserInfo> getStudentIfo(GetStudentIfoVo getStudentIfoVo) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不能查看",2333);
        }
        Page<UserInfo>page=new Page<>(getStudentIfoVo.getCurrent(),getStudentIfoVo.getSize());
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer HrId = (Integer) stringObjectMap.get("id");
        Page<UserInfo> userInfos = userInfoMapper.selectListStudentInfo(page ,HrId, getStudentIfoVo.getStudentName());
        return userInfos;
    }

    @Override
    public Page<GetPositionInfoDTO> getPositionInfo(Position1Vo position1Vo) {
        if(permissionVerification()!=3)
        {
            throw new Myexception("您不是HR不能查看",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer HrId = (Integer) stringObjectMap.get("id");
        Page<GetPositionInfoDTO>page=new Page<>(position1Vo.getCurrent(),position1Vo.getSize());
        Page<GetPositionInfoDTO>info= userInfoMapper.GetPositionInfoDTO(page,HrId,position1Vo.getPositionName(),position1Vo.getIsAccept());

        return info;
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

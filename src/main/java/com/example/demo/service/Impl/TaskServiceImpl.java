package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.exception.Myexception;
import com.example.demo.mapper.*;
import com.example.demo.pojo.*;
import com.example.demo.pojo.dto.InternshipInfoDTO;
import com.example.demo.pojo.dto.OPinionDTO;
import com.example.demo.pojo.dto.StudentTaskDTO;
import com.example.demo.pojo.vo.*;
import com.example.demo.service.TaskService;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentTaskMapper studentTaskMapper;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private OpinionMapper opinionMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishTask(PublishTask publishTask) {
        if(permissionVerification()!=2)
        {
            throw new Myexception("您不是老师无法发布任务",110);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        Task task1 = new Task();
        BeanUtils.copyProperties(publishTask,task1);
        task1.setTeacherId(teacherId);
        taskMapper.insertTask(task1);
        System.out.println(task1.getId()+"dsjhfkshdfjkshfkshfldshfklshflksjfklas;fja;fjakl;");

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>().eq(User::getTeacherId,teacherId);
        List<User> students = userMapper.selectList(userLambdaQueryWrapper);
        if(students==null)
        {
            throw new Myexception("您没有学生",110);
        }
        for(User student:students)
        {
            if(student==null)
            {
                break;
            }

            StudentTask studentTask = new StudentTask();
            studentTask.setTaskId(task1.getId());

            studentTask.setStudentId(student.getId());
            QueryWrapper<UserInfo> queryWrapper1 = new QueryWrapper<UserInfo>().eq("id", student.getId());
            UserInfo userInfo = userInfoMapper.selectOne(queryWrapper1);
            studentTask.setName(userInfo.getName());

            studentTaskMapper.insert(studentTask);
        }


    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // 每天晚上 12 点执行
    public void sendMsg() {
        QueryWrapper<User> query = new QueryWrapper<User>().eq("type", 2);
        List<User> teachers = userMapper.selectList(query);
        for (User teacher : teachers) {

            QueryWrapper<Task> queryWrapper1 = new QueryWrapper<Task>().eq("teacher_id", teacher.getId());
            List<Task> tasks = taskMapper.selectList(queryWrapper1);
            if(tasks==null)
            {
                continue;
            }
            for (Task obj : tasks) {
                if (obj.getDeadline().isBefore(LocalDateTime.now()))//截止时间后
                {
                    QueryWrapper<StudentTask> queryWrapper2 = new QueryWrapper<StudentTask>().eq("task_id", obj.getId());
                    List<StudentTask> studentTasks = studentTaskMapper.selectList(queryWrapper2);
                    if(studentTasks!=null)
                    {
                        for (StudentTask studentTask : studentTasks) {
                           if(studentTask.getCompleteStatus()==0)
                           {
                               QueryWrapper<User> queryWrapper3 = new QueryWrapper<User>().eq("id", studentTask.getStudentId());
                               User stu = userMapper.selectOne(queryWrapper3);

                               SimpleMailMessage message = new SimpleMailMessage();
                               message.setFrom(teacher.getEmail());//老师邮箱
                               message.setTo(stu.getEmail());
                               message.setSubject("任务未完成提醒");
                               message.setText("您的任务"+obj.getTask()+"未完成");
                               if(message.getText()==null) {

                                   throw new Myexception("发送失败",2333);
                               }
                               mailSender.send(message);
                               System.out.println(stu);
                               System.out.println(obj.getTask());
                           }

                        }
                    }

                }

            }
        }
    }

    @Override
    public Page<Task> getTask(GetContentVo getContentVo ) {
       if( permissionVerification()!=2){
           throw new Myexception("您不是老师不能进行该操作",2333);
       }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        Page<Task> page = new Page<>(getContentVo.getCurrent(), getContentVo.getSize());

        Page<Task> taskPage = taskMapper.selectPageByTitle(page,getContentVo.getTitle(),teacherId);
       return taskPage;
    }

    @Override
    public Page<StudentTaskDTO> getContent(GetContentVo getContentVo) {
        if( permissionVerification()!=2){
            throw new Myexception("您不是老师不能进行该操作",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        Page<StudentTaskDTO> page = new Page<>(getContentVo.getCurrent(), getContentVo.getSize());

        Page<StudentTaskDTO> task = taskMapper.selectOneByTaskId(page,getContentVo.getTaskId(),teacherId,getContentVo.getStudentId(),getContentVo.getName());
return task;
    }

    @Override
    public Page<OPinionDTO> getOpinion(GetContentVo getContentVo) {
    if( permissionVerification()!=2){
        throw new Myexception("你不是老师无该权限",2333);
    }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        Page<OPinionDTO> page = new Page<>(getContentVo.getCurrent(), getContentVo.getSize());
        Page<OPinionDTO> opinion1 = opinionMapper.getOpinion(page, teacherId, getContentVo.getStudentId(), getContentVo.getName());
        return opinion1;
//        List<Opinion> opinions = new ArrayList<>();
//
//        if(getContentVo.getStudentId()==null)
//        {
//            LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<User>().eq(User::getTeacherId, teacherId);
//            Page<User> userPage = userMapper.selectPage(page, lambdaQueryWrapper);
//
//            List<User> students = userPage.getRecords();
//            for (User student : students) {
//                LambdaQueryWrapper<Opinion>lambdaQueryWrapper1=new LambdaQueryWrapper<Opinion>().eq(Opinion::getStudentId, student.getId());
//                Opinion opinion = opinionMapper.selectOne(lambdaQueryWrapper1);
//                if(opinion!=null) {
//                    opinions.add(opinion);
//                }
//            }
//            return opinions;
//        }
//        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<User>().eq(User::getTeacherId, teacherId).eq(User::getId, getContentVo.getStudentId());
//        User user = userMapper.selectOne(lambdaQueryWrapper);
//        if(user==null)
//        {
//            {
//                throw new Myexception("您没有该学生",2333);
//            }
//        }
//        LambdaQueryWrapper<Opinion>lambdaQueryWrapper1=new LambdaQueryWrapper<Opinion>().eq(Opinion::getStudentId,getContentVo.getStudentId());
//        Page<Opinion> page1 = new Page<>(getContentVo.getCurrent(), getContentVo.getSize());
//        Page<Opinion> opinions1 = opinionMapper.selectPage(page1,lambdaQueryWrapper1);
//
//        return opinions1.getRecords();
    }

    @Override
    public void addevaluate(Evaluate evaluate) {
        if( permissionVerification()!=2){
            throw new Myexception("你不是老师无该权限",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<Task> eq1 = new LambdaQueryWrapper<Task>().eq(Task::getTeacherId,teacherId).eq(Task::getId,evaluate.getTaskId());
        LambdaQueryWrapper<User> eq2 = new LambdaQueryWrapper<User>().eq(User::getId,evaluate.getStudentId());
        Task task = taskMapper.selectOne(eq1);
        User student = userMapper.selectOne(eq2);
        if(!Objects.equals(task.getTeacherId(), teacherId))
        {
            throw new Myexception("没有该发布该任务无法评价",120);
        }
        if(!student.getTeacherId().equals(teacherId))
        {
            throw new Myexception("不是您的学生无法评价",120);
        }

        LambdaQueryWrapper<StudentTask> eq = new LambdaQueryWrapper<StudentTask>().eq(StudentTask::getTaskId, evaluate.getTaskId()).eq(StudentTask::getStudentId, evaluate.getStudentId());
        StudentTask studentTask = studentTaskMapper.selectOne(eq);
        if(studentTask==null)
        {
            throw  new Myexception("没有该内容无法评价",120);
        }
        if(studentTask.getEvaluateStatus()==0)
        {
            studentTask.setEvaluate(evaluate.getEvaluate());
            studentTask.setEvaluateStatus(1);
            studentTaskMapper.updateById(studentTask);
        }else{
            throw new Myexception("已经评价过无法评价",120);
        }

    }

    @Override
    public Page<UserInfo> getInfo(InternshipStatusVo internshipStatusVo) {
        if( permissionVerification()!=2){
            throw new Myexception("你不是老师无该权限",2333);
        }
        Page<UserInfo>page=new Page<>(internshipStatusVo.getCurrent(),internshipStatusVo.getSize());
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        Page<UserInfo> userInfos = userInfoMapper.selectListUserInfo(page,internshipStatusVo.getInternshipStatus(), teacherId, internshipStatusVo.getName());


        return userInfos;
    }

    @Override
    public Page<InternshipInfoDTO> getInternshipInfo(InternshipInfoVo internshipInfoVo) {
        if( permissionVerification()!=2){
            throw new Myexception("你不是老师无该权限",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");

        Page<InternshipInfoDTO> page = new Page<>(internshipInfoVo.getCurrent(),internshipInfoVo.getSize());
        Page<InternshipInfoDTO>  userInfos=  userMapper.selectInternshipInfoDTO(page,internshipInfoVo.getName(), teacherId);
        return userInfos;
    }

    @Override
    public void StartInternship(Integer studentId) {
        if( permissionVerification()!=2){
            throw new Myexception("你不是老师无该权限",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<User> ex = new LambdaQueryWrapper<User>().eq(User::getId, studentId);
        User stu = userMapper.selectOne(ex);
        if(stu==null)
        {
            throw new Myexception("无该学生",2333);
        }
        if(!stu.getTeacherId().equals(teacherId))
        {
            throw new Myexception("您没有该学生",2333);
        }
        LambdaQueryWrapper<UserInfo> ex1 = new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, studentId);
        UserInfo userInfo = userInfoMapper.selectOne(ex1);
        if(userInfo==null)
        {
            throw new Myexception("该学生没有完成基本信息",2333);
        }
        userInfo.setInternshipStatus(1);
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public Page<StudentTask> getOneComplete(GetContentVo getContentVo) {
        if( permissionVerification()!=2){
            throw new Myexception("你不是老师无该权限",2333);
        }
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer teacherId = (Integer) stringObjectMap.get("id");
        LambdaQueryWrapper<User> ex = new LambdaQueryWrapper<User>().eq(User::getId, getContentVo.getStudentId());
        User stu = userMapper.selectOne(ex);
        if(stu==null)
        {
            throw new Myexception("无该学生",2333);
        }
        if(!stu.getTeacherId().equals(teacherId))
        {
            throw new Myexception("您没有该学生",2333);
        }
        Page<StudentTask> page = new Page<>(getContentVo.getCurrent(),getContentVo.getSize());
        Page<StudentTask> studentTasks = studentTaskMapper.selectPage(page,new LambdaQueryWrapper<StudentTask>().eq(StudentTask::getStudentId, getContentVo.getStudentId()));
        return studentTasks;
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

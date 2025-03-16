package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.enums.MyExcptionEnum;
import com.example.demo.exception.Myexception;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.JavaCache;
import com.example.demo.pojo.RegisterUser;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.vo.LoginVo;
import com.example.demo.pojo.vo.PasswordVo;
import com.example.demo.pojo.vo.UserInfoVo;
import com.example.demo.pojo.vo.UserVo;
import com.example.demo.result.ResponseResult;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTUtil;
import com.example.demo.util.Md5Util;
import com.example.demo.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired//发送邮件
    private JavaMailSender mailSender;
    @Autowired
    private UserMapper userMapper;
    @Override
    public LoginVo login(UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("user_name", userVo.getUserName())
                .eq("password", Md5Util.getMD5String(userVo.getPassword()));
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new Myexception(MyExcptionEnum.ACCOUNT_UNREGISTER);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", user.getUserName());
        map.put("id", user.getId());


        if (JWTUtil.generateToken(map).isEmpty()) {
            throw new Myexception("操作失败", 100);

        }

        String token = JWTUtil.generateToken(map);
        return new LoginVo(token, user.getType(), user.getId());
    }

    @Override
    public void sendCaptcha(String emailname) {
        if(emailname==null) {

            throw new Myexception("请输入邮箱",2400);
        }
        Random random = new Random();
        // 生成一个范围在 100000 到 999999 之间的随机六位数
        Integer num=100000 + random.nextInt(900000);
        String captcha = num.toString();
        SimpleMailMessage message = new SimpleMailMessage();
        //把验证码放入缓存库中
        JavaCache.put(emailname,captcha);
        message.setFrom("1774012815@qq.com");
        message.setTo(emailname);
        message.setSubject("验证码");
        message.setText("您的验证码是：" + captcha + "有效期为5分钟");

        if(message.getText()==null) {

            throw new Myexception(MyExcptionEnum.CODE_GET_ERROR);
        }
        mailSender.send(message);
    }

    @Override
    @Transactional
    public void register(RegisterUser registerUser) {
        //判断两次密码是否相同
        if (!registerUser.getPassword().equals(registerUser.getConfirmPassword())) {
            throw new Myexception(MyExcptionEnum.PASSWORD_IS_NOT_TRUE);
        }
        if ((registerUser.getCaptcha()==null)||!registerUser.getCaptcha().equals(JavaCache.get(registerUser.getEmail()))) {

            throw new Myexception(MyExcptionEnum.CODE_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("email", registerUser.getEmail());
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new Myexception(MyExcptionEnum.ACCOUNT_REPEAT);
        }
        User user1 = new User();
        BeanUtils.copyProperties(registerUser, user1);
        //加密
        user1.setPassword( Md5Util.getMD5String(registerUser.getPassword()));



        userMapper.insert(user1);
    }

    @Override
    public void logout() {
        ThreadLocalUtil.remove();

    }

    @Override
    @Transactional
    public void updatePassword(PasswordVo passwordVo) {
        if(!passwordVo.getNewPassword().equals(passwordVo.getConfirmPassword())){
            throw new Myexception(MyExcptionEnum.PASSWORD_IS_NOT_TRUE);
        }


        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        //
       if(!Md5Util.getMD5String(passwordVo.getOldPassword()).equals(user.getPassword()))
       {
           throw new Myexception("原密码错误",120);
       }
        if(passwordVo.getNewPassword().equals(passwordVo.getOldPassword())){
            throw new Myexception("新旧密码不能相同",199);
        }
        user.setPassword(Md5Util.getMD5String(passwordVo.getNewPassword()));
        userMapper.updateById(user);
    }


    /**
     * 权限校验
     */
    public Integer permissionVerification() {
        Map<String, Object> stringObjectMap = ThreadLocalUtil.get();
        Integer id = (Integer) stringObjectMap.get("id");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", id);
        User user = userMapper.selectOne(queryWrapper);

        //throw new Myexception("您不是管理员没有该权限",2400);

        return user.getType();
    }

}



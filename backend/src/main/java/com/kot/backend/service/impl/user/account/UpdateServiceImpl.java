package com.kot.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kot.backend.mapper.UserMapper;
import com.kot.backend.pojo.User;
import com.kot.backend.service.impl.utils.UserDetailsImpl;
import com.kot.backend.service.user.account.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> update(Map<String, String> data) {

        UsernamePasswordAuthenticationToken authenticationToken=
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser=(UserDetailsImpl) authenticationToken.getPrincipal();

        User user=loginUser.getUser();

        int user_id=Integer.parseInt(data.get("user_id"));

        String username=data.get("username");
        String password=data.get("password");
        String confirmedPassword=data.get("confirmedPassword");
        String photo=data.get("photo");

        Map<String,String> map=new HashMap<>();
        if (username==null){
            map.put("error_message","用户名不能为空");
            return map;
        }
        if (password==null||confirmedPassword==null){
            map.put("error_message","密码不能为空");
            return map;
        }
        username=username.trim();
        if (username.length()==0){
            map.put("error_message","用户名不能为空");
            return map;
        }
        if (password.length()==0||confirmedPassword.length()==0){
            map.put("error_message","密码不能为空");
            return map;
        }
        if (username.length()>100){
            map.put("error_message","用户名长度不能大于100");
            return map;
        }
        if (password.length()>100||confirmedPassword.length()>100){
            map.put("error_message","密码长度不能大于100");
            return map;
        }
        if (!password.equals(confirmedPassword)){
            map.put("error_message","两次输入的密码不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> users=userMapper.selectList(queryWrapper);
        if (users.size()>1){
            map.put("error_message","用户名已存在");
            return map;
        }
        String encodedPassword=passwordEncoder.encode(password);
        String sign= data.get("sign");
        User new_user=
                new User(user.getId(),username,encodedPassword,photo,sign);
        userMapper.updateById(new_user);
        map.put("error_message","success");
        return map;
    }
}

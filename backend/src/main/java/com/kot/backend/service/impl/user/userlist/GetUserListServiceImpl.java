package com.kot.backend.service.impl.user.userlist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kot.backend.mapper.UserMapper;
import com.kot.backend.pojo.User;
import com.kot.backend.service.user.userlist.GetUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetUserListServiceImpl implements GetUserListService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getuserlist(Integer page) {

        IPage<User> userIPage=new Page<>(page,10);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<User> users=userMapper.selectPage(userIPage,queryWrapper).getRecords();
        JSONObject resp=new JSONObject();
        resp.put("users",users);
        resp.put("users_count",userMapper.selectCount(null));
        return resp;
    }
}

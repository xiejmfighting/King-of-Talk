package com.kot.backend.service.impl.user.post;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kot.backend.mapper.PostMapper;
import com.kot.backend.mapper.UserMapper;
import com.kot.backend.pojo.Post;
import com.kot.backend.pojo.User;
import com.kot.backend.service.user.post.GetUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserProfileServiceImpl implements GetUserProfileService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;

    @Override
    public JSONObject getuserprofile(Integer userId) {
        User user=userMapper.selectById(userId);

        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());

        List<Post> posts=postMapper.selectList(queryWrapper);

        JSONObject resp=new JSONObject();
        resp.put("user",user);
        resp.put("posts",posts);

        return resp;
    }
}

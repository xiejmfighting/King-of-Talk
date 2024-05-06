package com.kot.backend.service.impl.postlist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kot.backend.mapper.PostMapper;
import com.kot.backend.mapper.UserMapper;
import com.kot.backend.pojo.Post;
import com.kot.backend.pojo.User;
import com.kot.backend.service.postlist.GetPostListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetPostListServiceImpl implements GetPostListService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public JSONObject getlist(Integer page) {

        IPage<Post> postIPage=new Page<>(page,5);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Post> posts=postMapper.selectPage(postIPage,queryWrapper).getRecords();
        JSONObject resp=new JSONObject();
        List<JSONObject> items=new LinkedList<>();
        for (Post post:posts){
            User user=userMapper.selectById(post.getUserId());
            JSONObject item=new JSONObject();
            item.put("username",user.getUsername());
            item.put("photo",user.getPhoto());
            item.put("post",post);
            items.add(item);
        }
        resp.put("posts",items);
        resp.put("posts_count",postMapper.selectCount(null));
        return resp;
    }
}

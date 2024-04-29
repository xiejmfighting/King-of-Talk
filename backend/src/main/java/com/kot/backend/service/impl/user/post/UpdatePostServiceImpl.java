package com.kot.backend.service.impl.user.post;

import com.kot.backend.mapper.PostMapper;
import com.kot.backend.pojo.Post;
import com.kot.backend.pojo.User;
import com.kot.backend.service.impl.utils.UserDetailsImpl;
import com.kot.backend.service.user.post.UpdatePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdatePostServiceImpl implements UpdatePostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {

        UsernamePasswordAuthenticationToken authenticationToken=
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser=(UserDetailsImpl) authenticationToken.getPrincipal();

        User user=loginUser.getUser();

        int post_id=Integer.parseInt(data.get("post_id"));

        String title=data.get("title");
        String description=data.get("description");
        String content=data.get("content");

        Map<String,String> map=new HashMap<>();

        if (title.length()==0||title==null){
            map.put("error_message","帖子标题不能为空");
            return map;
        }
        if (title.length()>100){
            map.put("error_message","帖子标题长度不能大于100");
            return map;
        }

        if (description!=null&&description.length()>100){
            map.put("error_message","帖子简介长度不能大于100");
            return map;
        }
        if (content.length()==0||content==null){
            map.put("error_message","帖子内容不能为空");
            return map;
        }
        if (content.length()>10000){
            map.put("error_message","帖子字数不能大于10000");
            return map;
        }

        Post post=postMapper.selectById(post_id);

        if (post==null){
            map.put("error_message","该帖子不存在或已被删除");
            return map;
        }
        if (!post.getUserId().equals(user.getId())){
            map.put("error_message","没有权限修改该帖子");
            return map;
        }

        Post new_post=new Post(
                post.getId(), user.getId(),title,description,content,post.getCreatetime(),new Date()
        );

        postMapper.updateById(new_post);
        map.put("error_message","success");

        return map;
    }
}

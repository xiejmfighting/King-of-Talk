package com.kot.backend.service.impl.user.post;

import com.kot.backend.mapper.PostMapper;
import com.kot.backend.pojo.Post;
import com.kot.backend.pojo.User;
import com.kot.backend.service.impl.utils.UserDetailsImpl;
import com.kot.backend.service.user.post.DeletePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeletePostServiceImpl implements DeletePostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public Map<String, String> delete(Map<String, String> data) {

        UsernamePasswordAuthenticationToken authenticationToken=
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser=(UserDetailsImpl) authenticationToken.getPrincipal();
        User user=loginUser.getUser();

        int post_id=Integer.parseInt(data.get("post_id"));

        Post post=postMapper.selectById(post_id);

        Map<String,String> map=new HashMap<>();

        if (post==null){
            map.put("error_message","该帖子不存在或已被删除");
            return map;
        }
        if (!post.getUserId().equals(user.getId())){
            map.put("error_message","没有权限删除该帖子");
            return map;
        }
        postMapper.deleteById(post_id);
        map.put("error_message","success");
        return map;

    }
}

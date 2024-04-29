package com.kot.backend.service.impl.user.post;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kot.backend.mapper.PostMapper;
import com.kot.backend.pojo.Post;
import com.kot.backend.pojo.User;
import com.kot.backend.service.impl.utils.UserDetailsImpl;
import com.kot.backend.service.user.post.GetListPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class GetListPostServiceImpl implements GetListPostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Post> getList() {

        UsernamePasswordAuthenticationToken authenticationToken=
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser=(UserDetailsImpl) authenticationToken.getPrincipal();

        User user=loginUser.getUser();

        QueryWrapper queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("user_id",user.getId());

        return postMapper.selectList(queryWrapper);
    }
}

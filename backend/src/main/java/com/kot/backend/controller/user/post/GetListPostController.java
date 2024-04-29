package com.kot.backend.controller.user.post;

import com.kot.backend.pojo.Post;
import com.kot.backend.service.user.post.GetListPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListPostController {

    @Autowired
    private GetListPostService getListPostService;

    @GetMapping("/user/post/getlist/")
    public List<Post> getlist(){
        return getListPostService.getList();
    }
}

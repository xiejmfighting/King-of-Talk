package com.kot.backend.controller.user.post;

import com.kot.backend.service.user.post.UpdatePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdatePostController {
    @Autowired
    private UpdatePostService updatePostService;

    @PostMapping("/user/post/update/")
    public Map<String,String > update(@RequestParam Map<String,String> data){
        return updatePostService.update(data);
    }

}

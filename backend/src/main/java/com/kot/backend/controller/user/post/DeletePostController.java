package com.kot.backend.controller.user.post;

import com.kot.backend.service.user.post.DeletePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeletePostController {
    @Autowired
    private DeletePostService deletePostService;
    @PostMapping("user/post/delete/")
    public Map<String,String > delete(@RequestParam Map<String,String> data){
        return deletePostService.delete(data);
    }

}

package com.kot.backend.controller.postlist;

import com.alibaba.fastjson2.JSONObject;
import com.kot.backend.service.postlist.GetPostListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetPostListController {
    @Autowired
    private GetPostListService getPostListService;

    @GetMapping("/post/getlist/")
    JSONObject getlist(@RequestParam Map<String ,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        return getPostListService.getlist(page);
    }
}
